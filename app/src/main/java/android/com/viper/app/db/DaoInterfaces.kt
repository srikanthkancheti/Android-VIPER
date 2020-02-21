package android.com.viper.app.db

import android.com.viper.model.response.CAT_IMAGES_TABLE
import android.com.viper.model.response.CatImagesModel
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CatImagesDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(points: List<CatImagesModel>)

  @Update
  fun update(pointTransaction: CatImagesModel)

  @Query("SELECT * FROM $CAT_IMAGES_TABLE")
  fun getCatImagesData(): DataSource.Factory<Int, CatImagesModel>

  @Query("DELETE FROM $CAT_IMAGES_TABLE")
  fun deleteAll()

  @Query("SELECT COUNT(*) FROM $CAT_IMAGES_TABLE")
  fun getCount(): Int
}
