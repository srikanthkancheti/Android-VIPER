package android.com.viper.model.network

import android.com.viper.model.response.CatDetailModel
import android.com.viper.model.response.CatImagesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

const val PATH_ARGUMENT = "path_arg"

const val PATH_IMAGES_SEARCH = "images/search"
const val PATH_IMAGES = "images/{$PATH_ARGUMENT}"
const val QUERY_LIMIT = "limit"
const val QUERY_PAGE = "page"
const val QUERY_MIME_TYPE = "mime_types"
const val QUERY_ORDER = "order"

interface CatsApi {

  @GET(PATH_IMAGES_SEARCH)
  fun getCatImages(
    @Query(QUERY_LIMIT) limit: Int,
    @Query(QUERY_PAGE) page: Int,
    @Query(QUERY_MIME_TYPE) mimeTypes: String,
    @Query(QUERY_ORDER) order: String
  ): Observable<List<CatImagesModel>>

  @GET(PATH_IMAGES)
  fun getCatDetail(@Path(PATH_ARGUMENT) imageId: String?): Observable<CatDetailModel>
}
