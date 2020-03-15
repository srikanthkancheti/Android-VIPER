package android.com.viper.model.network

import android.com.viper.model.response.category.CategoriesResponse
import android.com.viper.model.response.meals.MealDetailsResponse
import android.com.viper.model.response.meals.MealsResponse
import rx.Observable

interface Repository {
  fun getMealsCategories(): Observable<CategoriesResponse>
  fun getMealsSearchResults(requestQuery: String?): Observable<MealsResponse>
  fun getMealDetailData(mealId: String?): Observable<MealDetailsResponse>
  fun getMealsFilterResults(requestFilter: String?): Observable<MealsResponse>
}