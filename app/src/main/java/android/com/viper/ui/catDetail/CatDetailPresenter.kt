package android.com.viper.ui.catDetail

import android.com.viper.model.network.interceptor.NetworkAvailabilityMonitor
import android.com.viper.model.response.CatDetailModel
import android.com.viper.ui.errorhandling.NetworkPresenter
import javax.inject.Inject

/**
 * Presenter may be compared to a “director” who sends commands to Interactor and Router after receiving
 * the data about the user’s actions from View,
 * and also sends the data prepared for display from Interactor to View.
 */
class CatDetailPresenter @Inject constructor(
  private val catDetailInteractor: CatDetailInteractor,
  private val networkMonitor: NetworkAvailabilityMonitor
) : NetworkPresenter<CatDetailViewCallBacks, CatDetailRouter>() {

  private lateinit var imageId: String

  fun takeCatImageId(imageId: String) {
    this.imageId = imageId
  }

  override fun onTakeView(view: CatDetailViewCallBacks) {
    super.onTakeView(view)
    getCatDetailsFromApi(imageId)
  }

  private fun getCatDetailsFromApi(imageId: String) {
    view.setProgressVisibility(true)
    catDetailInteractor.execute(makeNetworkSubscriber({
      this.onCatImageDetailsReceived(it)
      view.setProgressVisibility(false)
    }, { throwable ->
      onCatDetailError(throwable.message.toString())
      view.setProgressVisibility(false)
    }), imageId)
  }

  fun onCatDetailError(errorText: String?) {
    if (networkMonitor.isOnline())
      errorText?.let { view.showIoError(it) }
    else
      view.showMissingInternetMessage()
  }

  fun onCatImageDetailsReceived(catDetailModel: CatDetailModel) {
    view.showCatImageView(catDetailModel)
  }

  fun catImageDetailRequested(catDetailModel: CatDetailModel) {
    router.showCatData(catDetailModel.toString())
  }
}