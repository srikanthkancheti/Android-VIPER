package android.com.viper.ui.mealDetail

import android.com.viper.model.network.interceptor.NetworkAvailabilityMonitor
import android.com.viper.model.response.meals.MealDetailsModel
import android.com.viper.ui.errorhandling.NetworkPresenter
import javax.inject.Inject

/**
 * Presenter may be compared to a “director” who sends commands to Interactor and Router after receiving
 * the data about the user’s actions from View,
 * and also sends the data prepared for display from Interactor to View.
 */
class MealDetailPresenter @Inject constructor(
  private val catDetailInteractor: MealDetailInteractor,
  private val networkMonitor: NetworkAvailabilityMonitor
) : NetworkPresenter<MealDetailView, MealDetailRouter>() {

  private lateinit var mealId: String

  fun takeMealImageId(mealId: String) {
    this.mealId = mealId
  }

  override fun onTakeView(view: MealDetailView) {
    super.onTakeView(view)
    getCatDetailsFromApi(mealId)
  }

  private fun getCatDetailsFromApi(imageId: String) {
    view.setProgressVisibility(true)
    catDetailInteractor.execute(makeNetworkSubscriber({
      if (it.meals.isNotEmpty())
        this.onMealDetailsReceived(it.meals[0])
      view.setProgressVisibility(false)
    }, { throwable ->
      onMealDetailError(throwable.message.toString())
      view.setProgressVisibility(false)
    }), imageId)
  }

  fun onMealDetailError(errorText: String?) {
    if (networkMonitor.isOnline())
      errorText?.let { view.showIoError(it) }
    else
      view.showMissingInternetMessage()
  }

  fun onMealDetailsReceived(mealDetailsModel: MealDetailsModel) {
    view.showMealDetailsView(mealDetailsModel)
  }
}