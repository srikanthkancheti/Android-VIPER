package android.com.viper.model.response.meals

interface MealInfo {
  var strCategory: String?
  var strArea: String?

  fun getMealListItemCategory() : String? {
    return strCategory
  }

  fun getMealListItenArea() : String? {
    return strArea
  }
}