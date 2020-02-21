package android.com.viper.model

import android.com.viper.app.db.CatImagesDao
import android.com.viper.app.db.ViperSampleDB
import android.com.viper.di.annotation.IoThread
import android.com.viper.di.annotation.UiThread
import android.com.viper.model.network.Repository
import android.com.viper.model.response.CatImagesModel
import androidx.paging.PagedList
import androidx.paging.toFlowable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import rx.Observable
import rx.Scheduler
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatImagesManager @Inject constructor(
  private val repository: Repository,
  private val viperSampleDB: ViperSampleDB,
  @IoThread private val ioScheduler: Scheduler,
  @UiThread private val uiScheduler: Scheduler,
  @IoThread private val rxIoScheduler: io.reactivex.Scheduler,
  @UiThread private val rxUiScheduler: io.reactivex.Scheduler
) {

  private val catImagesDao: CatImagesDao = viperSampleDB.points()
  private var catImagesDataPage = 0

  private val imagesErrorChannel = PublishSubject.create<Throwable>()
  private val catImageSubscriptions = CompositeSubscription()

  fun subscribeForHistoryErrors(subscriber: (Throwable) -> Unit): Subscription {
    return imagesErrorChannel.subscribe(subscriber)
  }

  fun getCatImagesDataObservable(): Flowable<PagedList<CatImagesModel>> {
    return catImagesDao.getCatImagesData().toFlowable(
      config = PagedList.Config.Builder()
        .setPageSize(DEFAULT_PAGE_SIZE)
        .setPrefetchDistance(DEFAULT_PAGE_SIZE) // give user enough room for smooth scrolling
        .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE)
        .setEnablePlaceholders(true) // placeholders needed to help us save scroll state
        .build(),
      boundaryCallback = ImagesBoundaryCallback(),
      fetchScheduler = rxIoScheduler,
      notifyScheduler = rxUiScheduler,
      backpressureStrategy = BackpressureStrategy.LATEST
    )
  }

  private fun fetchFirstPageFromRemote(networkFetchCompletedCallback: ((Int) -> Unit)? = null) {
    catImagesDataPage = 0
    catImageSubscriptions.add(
      repository.getCatImagesData(DEFAULT_PAGE_SIZE, catImagesDataPage)
        .flatMap { content ->
          viperSampleDB.runInTransaction {
            catImagesDao.deleteAll()
            catImagesDao.insert(content)
          }
          return@flatMap Observable.just(content)
        }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(
          { content ->
            catImageSubscriptions.clear()
            networkFetchCompletedCallback?.invoke(content.size)
          },
          { throwable ->
            catImageSubscriptions.clear()
            imagesErrorChannel.onNext(throwable)
          }
        )
    )
  }

  fun fetchNextPageFromRemote(networkFetchCompletedCallback: ((Int) -> Unit)? = null) {
    catImagesDataPage += 1
    catImageSubscriptions.add(
      repository.getCatImagesData(DEFAULT_PAGE_SIZE, catImagesDataPage)
        .flatMap { content ->
          if (content.isNotEmpty()) {
            catImagesDao.insert(content)
          }
          return@flatMap Observable.just(content)
        }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(
          { content ->
            catImageSubscriptions.clear()
            networkFetchCompletedCallback?.invoke(content.size)
          },
          { throwable ->
            catImageSubscriptions.clear()
            imagesErrorChannel.onNext(throwable)
          }
        )
    )
  }

  // this is for pull down to refresh logic
  fun refreshCatImages(networkFetchCompletedCallback: ((Int) -> Unit)? = null) {
    catImageSubscriptions.clear()
    fetchFirstPageFromRemote(networkFetchCompletedCallback)
  }

  inner class ImagesBoundaryCallback : PagedList.BoundaryCallback<CatImagesModel>() {
    override fun onItemAtEndLoaded(itemAtEnd: CatImagesModel) {
      if (!catImageSubscriptions.hasSubscriptions()) {
        fetchNextPageFromRemote()
      }
    }

    override fun onZeroItemsLoaded() {
      super.onZeroItemsLoaded()
      fetchFirstPageFromRemote()
    }
  }

  companion object {
    private const val DEFAULT_PAGE_SIZE = 20
  }

}