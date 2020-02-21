package android.com.viper.ui.home

import android.com.viper.model.response.CatImagesModel
import com.dzaitsev.rxviper.Router

interface CatImagesRouter : Router {
  fun openCatImageDetailView(catImagesModel: CatImagesModel)
}