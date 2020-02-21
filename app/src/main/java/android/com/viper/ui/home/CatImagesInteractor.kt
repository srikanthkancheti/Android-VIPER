package android.com.viper.ui.home

import android.com.viper.app.db.ViperSampleDB
import android.com.viper.di.annotation.IoThread
import android.com.viper.di.annotation.UiThread
import android.com.viper.model.network.Repository
import android.com.viper.model.response.CatImagesModel
import androidx.paging.PagedList
import com.dzaitsev.rxviper.Interactor
import rx.Observable
import rx.Scheduler
import javax.inject.Inject

// class CatImagesInteractor @Inject constructor(
//   @IoThread subscribeOn: Scheduler,
//   @UiThread observeOn: Scheduler,
//   @IoThread private val rxIoScheduler: io.reactivex.Scheduler,
//   @UiThread private val rxUiScheduler: io.reactivex.Scheduler,
//   private val viperSampleDB: ViperSampleDB,
//   private val repository: Repository
// ) :
//   Interactor<Int, PagedList<CatImagesModel>>(subscribeOn, observeOn) {
//
//   override fun createObservable(page: Int?): Observable<PagedList<CatImagesModel>> {
//     return repository.getCatImagesData(page!!)
//   }
// }
