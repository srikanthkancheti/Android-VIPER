package android.com.viper.model.response.meals

import com.google.gson.annotations.SerializedName

data class MealDetailsResponse(@SerializedName("meals") var meals: List<MealDetailsModel>)