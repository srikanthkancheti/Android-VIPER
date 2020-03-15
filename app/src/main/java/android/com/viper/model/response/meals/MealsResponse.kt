package android.com.viper.model.response.meals

import com.google.gson.annotations.SerializedName

data class MealsResponse(@SerializedName("meals") var meals: List<MealModel>)