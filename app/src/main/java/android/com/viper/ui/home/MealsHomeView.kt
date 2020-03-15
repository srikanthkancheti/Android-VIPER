package android.com.viper.ui.home

import android.com.viper.model.response.category.Category
import android.com.viper.model.response.meals.MealModel
import android.com.viper.ui.errorhandling.NetworkView

interface MealsHomeView : NetworkView {
  fun showMealsCategories(categories: List<Category>)
  fun showingResultsForView(resultsForQuery: String)
  fun showMealsList(meals: List<MealModel>)
  fun showViewsForSearchResults()
  fun showViewsForCategoryResults()
}