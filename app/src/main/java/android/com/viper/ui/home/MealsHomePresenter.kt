package android.com.viper.ui.home

import android.com.viper.model.network.interceptor.NetworkAvailabilityMonitor
import android.com.viper.ui.errorhandling.NetworkPresenter
import javax.inject.Inject

class MealsHomePresenter @Inject constructor(
  private val mealsCategoriesInteractor: MealsCategoriesInteractor,
  private val mealsSearchInteractor: MealsSearchInteractor,
  private val mealFilterInteractor: MealsFilterInteractor,
  private val networkMonitor: NetworkAvailabilityMonitor
) : NetworkPresenter<MealsHomeView, MealsHomeRouter>() {

  var isCategoryLoaded = false

  private fun getCategoryStatus(): Boolean {
    return this.isCategoryLoaded
  }

  override fun onTakeView(view: MealsHomeView) {
    super.onTakeView(view)
    if (!getCategoryStatus())
      getCategoriesFromApi()
  }

  /*
   * we need to load the categories only once and save them in local DB
   * pull from there when ever required
   */
  private fun getCategoriesFromApi() {
    view.setProgressVisibility(true)
    mealsCategoriesInteractor.execute(makeNetworkSubscriber({
      view.showMealsCategories(it.categories)
      view.setProgressVisibility(false)
      isCategoryLoaded = true
    }, { throwable ->
      onMealsHomeError(throwable.message.toString())
      view.setProgressVisibility(false)
    }))
  }

  private fun onMealsHomeError(errorText: String?) {
    if (networkMonitor.isOnline())
      errorText?.let { view.showIoError(it) }
    else
      view.showMissingInternetMessage()
  }

  fun getMealSearchResults(searchMeal: String) {
    view.setProgressVisibility(true)
    view.showViewsForSearchResults()
    mealsSearchInteractor.execute(makeNetworkSubscriber({
      view.showingResultsForView(searchMeal)
      view.showMealsList(it.meals)
      view.setProgressVisibility(false)
    }, { throwable ->
      onMealsHomeError(throwable.message.toString())
      view.setProgressVisibility(false)
    }), searchMeal)
  }

  fun categoryFilterRequested(strCategory: String) {
    view.setProgressVisibility(true)
    view.showViewsForSearchResults()
    mealFilterInteractor.execute(makeNetworkSubscriber({
      view.showingResultsForView(strCategory)
      view.showMealsList(it.meals)
      view.setProgressVisibility(false)
    }, { throwable ->
      onMealsHomeError(throwable.message.toString())
      view.setProgressVisibility(false)
    }), strCategory)
  }
}