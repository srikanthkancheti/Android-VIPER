package android.com.viper.ui.catDetail

import com.dzaitsev.rxviper.Router

/**
 * Router handles commands from Presenter to navigate between the screens.
 * Everything is quite simple here: Router receives a command from Presenter and,
 * having a link to Activity, navigates through the app views.
 * It is also worth mentioning that Router is responsible for passing data between screens.
 */
interface CatDetailRouter : Router {
  fun showCatData(catDetails: String)
}