package android.com.viper.model.response.category

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(@SerializedName("categories") var categories: List<Category>)