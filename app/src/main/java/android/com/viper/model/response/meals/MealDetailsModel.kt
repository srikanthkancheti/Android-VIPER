package android.com.viper.model.response.meals

import com.google.gson.annotations.SerializedName

data class MealDetailsModel(
  @SerializedName("idMeal") var idMeal: String,
  @SerializedName("strMeal") var strMeal: String,
  @SerializedName("strDrinkAlternate") var strDrinkAlternate: String,
  @SerializedName("strCategory") override var strCategory: String?,
  @SerializedName("strArea") override var strArea: String?,
  @SerializedName("strInstructions") var strInstructions: String,
  @SerializedName("strMealThumb") var strMealThumb: String,
  @SerializedName("strTags") override var strTags: String?,
  @SerializedName("strYoutube") override var strYoutube: String?,
  @SerializedName("strIngredient1") var strIngredient1: String,
  @SerializedName("strIngredient2") var strIngredient2: String,
  @SerializedName("strIngredient3") var strIngredient3: String,
  @SerializedName("strIngredient4") var strIngredient4: String,
  @SerializedName("strIngredient5") var strIngredient5: String,
  @SerializedName("strIngredient6") var strIngredient6: String,
  @SerializedName("strIngredient7") var strIngredient7: String,
  @SerializedName("strIngredient8") var strIngredient8: String,
  @SerializedName("strIngredient9") var strIngredient9: String,
  @SerializedName("strIngredient10") var strIngredient10: String,
  @SerializedName("strIngredient11") var strIngredient11: String,
  @SerializedName("strIngredient12") var strIngredient12: String,
  @SerializedName("strIngredient13") var strIngredient13: String,
  @SerializedName("strIngredient14") var strIngredient14: String,
  @SerializedName("strIngredient15") var strIngredient15: String,
  @SerializedName("strIngredient16") var strIngredient16: String,
  @SerializedName("strIngredient17") var strIngredient17: String,
  @SerializedName("strIngredient18") var strIngredient18: String,
  @SerializedName("strIngredient19") var strIngredient19: String,
  @SerializedName("strIngredient20") var strIngredient20: String,
  @SerializedName("strMeasure1") var strMeasure1: String,
  @SerializedName("strMeasure2") var strMeasure2: String,
  @SerializedName("strMeasure3") var strMeasure3: String,
  @SerializedName("strMeasure4") var strMeasure4: String,
  @SerializedName("strMeasure5") var strMeasure5: String,
  @SerializedName("strMeasure6") var strMeasure6: String,
  @SerializedName("strMeasure7") var strMeasure7: String,
  @SerializedName("strMeasure8") var strMeasure8: String,
  @SerializedName("strMeasure9") var strMeasure9: String,
  @SerializedName("strMeasure10") var strMeasure10: String,
  @SerializedName("strMeasure11") var strMeasure11: String,
  @SerializedName("strMeasure12") var strMeasure12: String,
  @SerializedName("strMeasure13") var strMeasure13: String,
  @SerializedName("strMeasure14") var strMeasure14: String,
  @SerializedName("strMeasure15") var strMeasure15: String,
  @SerializedName("strMeasure16") var strMeasure16: String,
  @SerializedName("strMeasure17") var strMeasure17: String,
  @SerializedName("strMeasure18") var strMeasure18: String,
  @SerializedName("strMeasure19") var strMeasure19: String,
  @SerializedName("strMeasure20") var strMeasure20: String,
  @SerializedName("strSource") var strSource: String,
  @SerializedName("dateModified") var dateModified: String
) : MealIngredientsInfo {
  override var allIngredients: String
    get() = "$strIngredient1, $strIngredient2, $strIngredient3, $strIngredient4, $strIngredient5, $strIngredient6, $strIngredient7, " +
      "$strIngredient8, $strIngredient9, $strIngredient10, $strIngredient11, $strIngredient12, $strIngredient13, $strIngredient14, " +
      "$strIngredient15, $strIngredient16, $strIngredient17, $strIngredient18, $strIngredient19, $strIngredient20"
    set(value) {}
  override var allMeasures: String
    get() = "$strMeasure1, $strMeasure2, $strMeasure3, $strMeasure4, $strMeasure5, $strMeasure6, $strMeasure7, " +
      "$strMeasure8, $strMeasure9, $strMeasure10, $strMeasure11, $strMeasure12, $strMeasure13, $strMeasure14, " +
      "$strMeasure15, $strMeasure16, $strMeasure17, $strMeasure18, $strMeasure19, $strMeasure20"
    set(value) {}

  override fun getMealCategory(): String? {
    return if (strCategory.isNullOrBlank()) "No Category" else strCategory
  }

  override fun getMealArea(): String? {
    return if (strArea.isNullOrBlank()) "No specific Area" else strArea
  }

  override fun getMealTags(): String? {
    return if (strTags.isNullOrBlank()) "No tags" else strTags
  }

  override fun getYoyTubeLink(): String? {
    return if (strYoutube.isNullOrBlank()) "" else strYoutube
  }
}