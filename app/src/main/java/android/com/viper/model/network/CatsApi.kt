package android.com.viper.model.network

import android.com.viper.model.response.category.CategoriesResponse
import android.com.viper.model.response.meals.MealDetailsResponse
import android.com.viper.model.response.meals.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

const val BASE_END_POINT = "/api/json/v1/1/"
const val CATEGORIES = "categories.php"
const val MEALS_SEARCH = "search.php"
const val MEALS_FILTER = "filter.php"
const val MEALS_DETAILS = "lookup.php"

const val QUERY_STRING = "s"
const val QUERY_LOOKUP = "i"
const val QUERY_CATEGORY = "c"

interface CatsApi {

  @GET(BASE_END_POINT + CATEGORIES)
  fun getCategories(): Observable<CategoriesResponse>

  @GET(BASE_END_POINT + MEALS_SEARCH)
  fun getMealSearchList(
    @Query(QUERY_STRING) requestQuery: String?
  ): Observable<MealsResponse>

  @GET(BASE_END_POINT + MEALS_DETAILS)
  fun getMealDetails(
    @Query(QUERY_LOOKUP) mealId: String?
  ): Observable<MealDetailsResponse>

  @GET(BASE_END_POINT + MEALS_FILTER)
  fun getFilterResults(
    @Query(QUERY_CATEGORY) requestFilter: String?
  ): Observable<MealsResponse>
}
