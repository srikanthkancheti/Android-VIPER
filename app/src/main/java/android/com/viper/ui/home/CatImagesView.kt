package android.com.viper.ui.home

import android.com.viper.model.response.CatImagesModel
import android.com.viper.ui.errorhandling.NetworkView
import androidx.paging.PagedList

interface CatImagesViewCallBacks : NetworkView {
  fun catImagesCollected(catImages: PagedList<CatImagesModel>)
  fun showCatImagesEmpty()
  fun showCatImagesError()
}