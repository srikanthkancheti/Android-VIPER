package android.com.viper.ui.home

import com.dzaitsev.rxviper.Router

interface MealsHomeRouter : Router {
  fun loadMealsCategories()
  fun navigateToMealDetail(idMeal: String, nameMeal: String)
}