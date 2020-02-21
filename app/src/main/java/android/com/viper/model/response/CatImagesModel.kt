package android.com.viper.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Entity refers to model objects used by Interactor. It is the simplest element of our VIPER structure.
 */
const val CAT_IMAGES_TABLE = "cat_images"

@Entity(tableName = CAT_IMAGES_TABLE)
data class CatImagesModel(
  @PrimaryKey
  @ColumnInfo(name = "cat_image_id")
  @SerializedName("id") var id: String,
  @ColumnInfo(name = "cat_image_url")
  @SerializedName("url") val url: String
) : Serializable