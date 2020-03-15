package android.com.viper.model.response.meals

interface MealIngredientsInfo {
  var allIngredients: String
  var allMeasures: String
  var strTags: String?
  var strCategory: String?
  var strArea: String?
  var strYoutube: String?

  fun getFormattedIngredients() : String {
    return allIngredients.replace(" ,","").replace("null,", "")
  }

  fun getFormattedMeasures() : String {
    return allMeasures.replace(" ,","").replace("null,", "")
  }

  fun getMealTags(): String? {
    return strTags
  }

  fun getMealCategory() : String? {
    return strCategory
  }

  fun getMealArea() : String? {
    return strArea
  }

  fun getYoyTubeLink() : String? {
    return strYoutube
  }
}