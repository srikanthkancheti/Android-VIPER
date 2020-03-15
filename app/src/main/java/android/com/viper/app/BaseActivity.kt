package android.com.viper.app

import android.com.viper.MealsApplication
import android.com.viper.R
import android.com.viper.di.component.BaseActivityComponent
import android.com.viper.di.modules.BaseActivityModule
import android.os.Bundle
import android.view.MenuItem
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
    activityComponent = MealsApplication.componentFromContext(this).plus(BaseActivityModule(this))
    setupComponent(activityComponent)
    containerView = findViewById<View>(android.R.id.content)
    setToolBarBack(isToolBarBackEnabled())
    updateToolBarTitle(setToolBarTitle())
  }

  protected abstract fun setupComponent(component: BaseActivityComponent)

  open fun setToolBarBack(enable: Boolean) {
    supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    supportActionBar?.setHomeButtonEnabled(enable)
  }

  private fun updateToolBarTitle(toolBarTitle: String) {
    supportActionBar?.title = toolBarTitle
  }

  // to disable initially
  open fun isToolBarBackEnabled(): Boolean {
    return false
  }

  open fun setToolBarTitle(): String {
    return getString(R.string.app_name)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
      return true;
    }
    return false;
  }

}