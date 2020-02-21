package android.com.viper.di.component

import android.com.viper.di.modules.BaseActivityModule
import android.com.viper.di.scope.ActivityScope
import android.com.viper.ui.catDetail.CatDetailActivity
import android.com.viper.ui.home.CatImagesActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(BaseActivityModule::class)])
interface BaseActivityComponent {

  fun inject(activity: CatImagesActivity)

  fun inject(activity: CatDetailActivity)

}