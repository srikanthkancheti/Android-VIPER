package android.com.viper.model.network

import android.com.viper.model.response.category.CategoriesResponse
import android.com.viper.model.response.meals.MealDetailsResponse
import android.com.viper.model.response.meals.MealsResponse
import android.content.Context
import retrofit2.Retrofit
import rx.Observable

open class AndroidViperRepository(
  retrofit: Retrofit,
  apiClass: Class<CatsApi>,
  val context: Context
): Repository {

  private val restAPI: CatsApi = retrofit.create(apiClass)

  override fun getMealsCategories(): Observable<CategoriesResponse> {
    return restAPI.getCategories()
  }

  override fun getMealsSearchResults(requestQuery: String?): Observable<MealsResponse> {
    return restAPI.getMealSearchList(requestQuery)
  }

  override fun getMealDetailData(mealId: String?): Observable<MealDetailsResponse> {
    return restAPI.getMealDetails(mealId)
  }

  override fun getMealsFilterResults(requestFilter: String?): Observable<MealsResponse> {
    return restAPI.getFilterResults(requestFilter)
  }
}