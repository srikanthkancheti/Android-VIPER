package android.com.viper.ui.mealDetail

import android.com.viper.model.response.meals.MealDetailsModel
import android.com.viper.ui.errorhandling.NetworkView

/**
 * View is responsible for displaying the user interface and sends events provided by the user to Presenter
 * Ideally, our View also not contain any logic, but only pass events to Presenter from the user and show what Presenter will say.
 * Due to this, the testability is being much improved
 */
interface MealDetailView : NetworkView {
  fun showMealDetailsView(mealDetailModel: MealDetailsModel)
  fun showMealImageInDetailsView(imageUrl: String)
}