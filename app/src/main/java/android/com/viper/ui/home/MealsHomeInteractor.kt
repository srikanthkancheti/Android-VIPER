package android.com.viper.ui.home

import android.com.viper.di.annotation.IoThread
import android.com.viper.di.annotation.UiThread
import android.com.viper.model.network.Repository
import android.com.viper.model.response.category.CategoriesResponse
import android.com.viper.model.response.meals.MealsResponse
import com.dzaitsev.rxviper.Interactor
import rx.Observable
import rx.Scheduler
import javax.inject.Inject

class MealsCategoriesInteractor @Inject constructor(
  @IoThread subscribeOn: Scheduler,
  @UiThread observeOn: Scheduler,
  private val repository: Repository
) :
  Interactor<Void, CategoriesResponse>(subscribeOn, observeOn) {
  override fun createObservable(void: Void?): Observable<CategoriesResponse> {
    return repository.getMealsCategories()
  }
}

class MealsSearchInteractor @Inject constructor(
  @IoThread subscribeOn: Scheduler,
  @UiThread observeOn: Scheduler,
  private val repository: Repository
) :
  Interactor<String, MealsResponse>(subscribeOn, observeOn) {
  override fun createObservable(requestQuery: String?): Observable<MealsResponse> {
    return repository.getMealsSearchResults(requestQuery)
  }
}

class MealsFilterInteractor @Inject constructor(
  @IoThread subscribeOn: Scheduler,
  @UiThread observeOn: Scheduler,
  private val repository: Repository
) :
  Interactor<String, MealsResponse>(subscribeOn, observeOn) {
  override fun createObservable(requestFilter: String?): Observable<MealsResponse> {
    return repository.getMealsFilterResults(requestFilter)
  }
}