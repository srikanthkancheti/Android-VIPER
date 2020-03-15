package android.com.viper.di.component

import android.com.viper.di.modules.BaseActivityModule
import android.com.viper.di.scope.ActivityScope
import android.com.viper.ui.mealDetail.MealDetailActivity
import android.com.viper.ui.home.MealsHomeActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(BaseActivityModule::class)])
interface BaseActivityComponent {

  fun inject(activity: MealsHomeActivity)

  fun inject(activity: MealDetailActivity)

}