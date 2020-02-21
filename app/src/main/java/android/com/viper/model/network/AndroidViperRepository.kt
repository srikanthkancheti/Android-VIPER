package android.com.viper.model.network

import android.com.viper.model.response.CatDetailModel
import android.com.viper.model.response.CatImagesModel
import android.content.Context
import retrofit2.Retrofit
import rx.Observable

open class AndroidViperRepository(
  retrofit: Retrofit,
  apiClass: Class<CatsApi>,
  val context: Context
): Repository {

  private val MIME_TYPES = "png"
  private val ORDER = "Desc"
  private val restAPI: CatsApi = retrofit.create(apiClass)

  override fun getCatImagesData(pageSize: Int, page: Int): Observable<List<CatImagesModel>> {
    return restAPI.getCatImages(pageSize, page, MIME_TYPES, ORDER)
  }

  override fun getCatDetailData(imageId: String?): Observable<CatDetailModel> {
    return restAPI.getCatDetail(imageId)
  }
}