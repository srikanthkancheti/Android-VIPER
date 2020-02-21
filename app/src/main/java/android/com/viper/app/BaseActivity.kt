package android.com.viper.app

import android.com.viper.CatApplication
import android.com.viper.di.component.BaseActivityComponent
import android.com.viper.di.modules.BaseActivityModule
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate

abstract class BaseActivity : SecureActivity() {

  private lateinit var activityComponent: BaseActivityComponent
  private lateinit var containerView: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Enable vector drawable compound views on prior lollipop
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    // Dagger setup
    activityComponent = CatApplication.componentFromContext(this).plus(BaseActivityModule(this))
    setupComponent(activityComponent)
    containerView = findViewById<View>(android.R.id.content)
  }

  protected abstract fun setupComponent(component: BaseActivityComponent)

}