package android.com.viper.ui.mealDetail

import android.com.viper.R
import android.com.viper.R.string
import android.com.viper.app.NetworkActivity
import android.com.viper.di.component.BaseActivityComponent
import android.com.viper.model.response.meals.MealDetailsModel
import android.com.viper.util.setVisible
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_meal_detail.*
import javax.inject.Inject

class MealDetailActivity : NetworkActivity(), MealDetailRouter, MealDetailView {

  private val mealId: String by lazy { intent.getStringExtra(ID_MEAL) }
  private val mealName: String by lazy { intent.getStringExtra(NAME_MEAL) }
  @Inject lateinit var presenter: MealDetailPresenter
  private lateinit var mealDetails: MealDetailsModel

  companion object {
    private val ID_MEAL: String = "id_meal"
    private val NAME_MEAL: String = "name_meal"

    fun start(context: Context, mealId: String, mealName: String) {
      val intent = Intent(context, MealDetailActivity::class.java)
      intent.putExtra(ID_MEAL, mealId)
      intent.putExtra(NAME_MEAL, mealName)
      context.startActivity(intent)
    }
  }

  override fun setupComponent(component: BaseActivityComponent) = component.inject(this)

  // To display ToolBar back icon
  override fun isToolBarBackEnabled(): Boolean {
    return true
  }

  override fun setToolBarTitle(): String {
    return mealName
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_meal_detail)

    presenter.takeMealImageId(mealId)

    listOf<View>(mealImageView).forEach { v ->
      v.setOnClickListener(onClickedEvent)
    }
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

  override fun showMealData(mealData: String) {
    // add action according to user interaction in detail screen
  }

  private val onClickedEvent = View.OnClickListener { v ->
    when (v) {
      mealImageView -> {
        showMealData(mealDetails.strMealThumb)
      }
    }
  }

  //Start of View callbacks region
  override fun showMealDetailsView(mealDetailModel: MealDetailsModel) {
    mealDetails = mealDetailModel

    headerDivider.setVisible(true)
    mealInstructionsTitleTextView.setVisible(true)
    mealIngredientTitleTextView.setVisible(true)
    mealMeasureTitleTextView.setVisible(true)

    bindResponseDataToViews(mealDetails)
  }

  override fun showMealImageInDetailsView(imageUrl: String) {
    Glide.with(this)
      .load(imageUrl)
      .diskCacheStrategy(DiskCacheStrategy.DATA)
      .into(mealImageView)
  }
  // End view call backs region

  private fun bindResponseDataToViews(mealDetails: MealDetailsModel) {
    mealTagsTextView.text = String.format(getString(string.tags_text), mealDetails.getMealTags())
    mealCategoryTextView.text = String.format(getString(string.category_text), mealDetails.getMealCategory())
    mealAreaTextView.text = String.format(getString(string.area_text), mealDetails.getMealArea())
    mealInstructionsTextView.text = mealDetails.strInstructions
    mealIngredientTextView.text = mealDetails.getFormattedIngredients()
    mealMeasureTextView.text = mealDetails.getFormattedMeasures()
    mealYouTubeLink.text = mealDetails.getYoyTubeLink()

    showMealImageInDetailsView(mealDetails.strMealThumb)
  }
}