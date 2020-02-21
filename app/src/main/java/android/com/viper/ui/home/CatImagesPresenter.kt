package android.com.viper.ui.home

import android.com.viper.model.CatImagesManager
import android.com.viper.model.network.interceptor.NetworkAvailabilityMonitor
import android.com.viper.model.response.CatImagesModel
import android.com.viper.ui.errorhandling.NetworkPresenter
import io.reactivex.disposables.CompositeDisposable
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class CatImagesPresenter @Inject constructor(
  private val catImagesManager: CatImagesManager,
  private val networkMonitor: NetworkAvailabilityMonitor
) : NetworkPresenter<CatImagesViewCallBacks, CatImagesRouter>() {

  private val catImagesubscriptions = CompositeSubscription()
  private val catImagesDisposable = CompositeDisposable()

  override fun onTakeView(view: CatImagesViewCallBacks) {
    super.onTakeView(view)

    if (!networkMonitor.isOnline()) {
      view.showMissingInternetMessage()
      // return
    }
  }

  fun subscribeToCatImageUpdates() {
    catImagesubscriptions.add(catImagesManager.subscribeForHistoryErrors {
      view.setProgressVisibility(false)
      view.showCatImagesError()
    })

    view.setProgressVisibility(true)
    catImagesDisposable.add(
      catImagesManager.getCatImagesDataObservable().subscribe { content ->
        view.catImagesCollected(content)
        view.setProgressVisibility(false)
      }
    )
  }

  fun requestCatImages() {
    // view.showCatImagesLoading(true)
    catImagesManager.fetchNextPageFromRemote { contentCount ->
      if (contentCount == 0) {
        view.showCatImagesEmpty()
      }
      // view.showCatImagesLoading(false)
    }
  }

  override fun onDropView(view: CatImagesViewCallBacks) {
    super.onDropView(view)
    catImagesubscriptions.clear()
    catImagesDisposable.clear()
  }

  fun catImageDetailRequested(catImagesModel: CatImagesModel) {
    router.openCatImageDetailView(catImagesModel)
  }
}