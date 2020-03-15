package android.com.viper.ui.home

import android.com.viper.R
import android.com.viper.app.NetworkActivity
import android.com.viper.app.adapter.CategoriesGridRecyclerAdapter
import android.com.viper.app.adapter.MealsGridRecyclerAdapter
import android.com.viper.di.component.BaseActivityComponent
import android.com.viper.model.response.category.Category
import android.com.viper.model.response.meals.MealModel
import android.com.viper.ui.mealDetail.MealDetailActivity
import android.com.viper.util.UiUtils
import android.com.viper.util.setOnClickListenerThrottled
import android.com.viper.util.setVisible
import android.com.viper.widgets.TextWatcherStub
import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_meals_home.*
import kotlinx.android.synthetic.main.appbar_search_white_layout.*
import kotlinx.android.synthetic.main.search_query_layout.*
import javax.inject.Inject
/*
 * This screen shows all meal categories available and search view
 * Meals search results also shown here and when select a meal item it will open the meal details
 */
class MealsHomeActivity : NetworkActivity(), MealsHomeView, MealsHomeRouter {

  @Inject lateinit var presenter: MealsHomePresenter
  private var categoriesAdapter: CategoriesGridRecyclerAdapter? = null
  private var mealsAdapter: MealsGridRecyclerAdapter? = null

  override fun setupComponent(component: BaseActivityComponent) = component.inject(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_meals_home)
    initView()
    initSearchView()
    initSearchQueryView()
  }

  private fun initSearchQueryView() {
    searchClearButton.setOnClickListenerThrottled {
      showViewsForCategoryResults()
      searchEditText.setText("")
    }
  }

  private fun initSearchView() {
    searchEditText.hint = getString(R.string.hint_search_meal)
    searchButton.setOnClickListenerThrottled {
      UiUtils.hideSoftKeyboard(searchEditText)
      presenter.getMealSearchResults(searchEditText.text.toString())
    }

    searchEditText.addTextChangedListener(object : TextWatcherStub() {
      override fun afterTextChanged(s: Editable) {
        if (s.toString().isNotEmpty()) searchButton.setVisible(true) else searchButton.setVisible(false)
      }
    })

    searchEditText.setOnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        presenter.getMealSearchResults(searchEditText.text.toString())
      }
      false
    }
  }

  /**
   * False = to enable taking screenshots
   * True = to disable taking screenshots
   */
  override fun isSecureScreen(): Boolean {
    return false
  }

  /**
   * False = to enable tool bar back
   * True = to disable tool bar back
   */
  override fun isToolBarBackEnabled(): Boolean {
    return false
  }

  private fun initView() {
    recyclerViewCategories.layoutManager = GridLayoutManager(this, 3)
    categoriesAdapter = CategoriesGridRecyclerAdapter { categoryModel -> presenter.categoryFilterRequested(categoryModel.strCategory) }
    recyclerViewCategories.adapter = categoriesAdapter

    recyclerViewMeals.layoutManager = GridLayoutManager(this, 3)
    mealsAdapter = MealsGridRecyclerAdapter { mealModel -> navigateToMealDetail(mealModel.idMeal, mealModel.strMeal) }
    recyclerViewMeals.adapter = mealsAdapter
  }

  override fun onStart() {
    super.onStart()
    presenter.takeView(this)
    presenter.takeRouter(this)
  }

  override fun onStop() {
    presenter.dropView(this)
    presenter.dropRouter(this)
    super.onStop()
  }

  /**
   * Start of View callbacks region
   */
  // View call back method to bing data to recycler view
  override fun showMealsCategories(categories: List<Category>) {
    showViewsForCategoryResults()
    categoriesAdapter?.updateCategoryList(categories)
  }

  override fun showingResultsForView(resultsForQuery: String) {
    searchQueryTextView.text = String.format(getString(R.string.results_for_text), resultsForQuery)
  }

  override fun showMealsList(meals: List<MealModel>) {
    mealsAdapter?.updateMealsList(meals)
  }

  override fun showViewsForSearchResults() {
    categoriesHeaderTextView.setVisible(false)
    recyclerViewCategories.setVisible(false)
    recyclerViewMeals.setVisible(true)
    searchQueryLayout.setVisible(true)
  }

  override fun showViewsForCategoryResults() {
    categoriesHeaderTextView.setVisible(true)
    recyclerViewCategories.setVisible(true)
    recyclerViewMeals.setVisible(false)
    searchQueryLayout.setVisible(false)
  }

  /**
   * End of View callbacks region
   */

  /**
   * Start of Router callbacks region
   */
  override fun loadMealsCategories() {

  }

  override fun navigateToMealDetail(idMeal: String, nameMeal: String) {
    MealDetailActivity.start(this, idMeal, nameMeal)
  }
  /**
   * End of Router callbacks region
   */
}
