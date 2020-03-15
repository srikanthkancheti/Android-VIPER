package android.com.viper.model.response.meals

import com.google.gson.annotations.SerializedName

data class MealModel(
  @SerializedName("idMeal") var idMeal: String,
  @SerializedName("strMeal") var strMeal: String,
  @SerializedName("strCategory") override var strCategory: String?,
  @SerializedName("strArea") override var strArea: String?,
  @SerializedName("strMealThumb") var strMealThumb: String
) : MealInfo {
  override fun getMealListItemCategory(): String? {
    return if (strCategory.isNullOrBlank()) "No Category" else strCategory
  }

  override fun getMealListItenArea(): String? {
    return if (strArea.isNullOrBlank()) "No specific Area" else strArea
  }
}