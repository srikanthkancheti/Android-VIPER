package android.com.viper.ui.catDetail

import android.com.viper.di.annotation.IoThread
import android.com.viper.di.annotation.UiThread
import android.com.viper.model.network.Repository
import android.com.viper.model.response.CatDetailModel
import com.dzaitsev.rxviper.Interactor
import rx.Observable
import rx.Scheduler
import javax.inject.Inject

/**
 * Interactor will retrieve the data from the source, convert it into ready-to-work one, and return it to Presenter.
 * To share the work entrusted to them and increase testability,
 * another layer called Repo (Repository) was added which is responsible for obtaining the data.
 */
class CatDetailInteractor @Inject constructor(
  @IoThread subscribeOn: Scheduler,
  @UiThread observeOn: Scheduler,
  private val repository: Repository
) :
  Interactor<String, CatDetailModel>(subscribeOn, observeOn) {

  override fun createObservable(imageId: String?): Observable<CatDetailModel> {
    return repository.getCatDetailData(imageId)
  }
}