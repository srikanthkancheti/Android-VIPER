package android.com.viper.model.network

import android.com.viper.model.response.CatDetailModel
import android.com.viper.model.response.CatImagesModel
import androidx.paging.PagedList
import rx.Observable

interface Repository {
  fun getCatImagesData(pageSize: Int, page: Int): Observable<List<CatImagesModel>>
  fun getCatDetailData(imageId: String?): Observable<CatDetailModel>
}