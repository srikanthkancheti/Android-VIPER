package android.com.viper.model.response

import com.google.gson.annotations.SerializedName

data class CatDetailModel(
  @SerializedName("id") var id: String,
  @SerializedName("url") var url: String,
  @SerializedName("width") var width: Int,
  @SerializedName("height") var height: Int
)