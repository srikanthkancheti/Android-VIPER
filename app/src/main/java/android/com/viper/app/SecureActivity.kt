package android.com.viper.app

import android.com.viper.BuildConfig
import android.os.Bundle
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity

open class SecureActivity : AppCompatActivity() {

  // on non production and debug builds we would like to have possibility to take screenshots - for testing
  private val SHOULD_USE_SECURE_SCREENS = BuildConfig.IS_PRODUCTION && !BuildConfig.DEBUG

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // enable flags only for secure screens
    setSecureScreenMode(isSecureScreen())
  }

  /**
   * Allows to modify secure screen flag dynamically, e.g. for activity with viewpager, where some pages should be secure
   *
   * @param enabled true to enable secure mode
   */
  open fun setSecureScreenMode(enabled: Boolean) {
    if (SHOULD_USE_SECURE_SCREENS && enabled) {
      window.addFlags(LayoutParams.FLAG_SECURE)
    } else {
      window.clearFlags(LayoutParams.FLAG_SECURE)
    }
  }

  /**
   * Defines if current activity should use SECURE flags to disable screenshots and clipboard.
   * Can be overriden to modify behavior.
   *
   * @return true if activity should be secure
   */
  open fun isSecureScreen(): Boolean {
    return SHOULD_USE_SECURE_SCREENS
  }
}