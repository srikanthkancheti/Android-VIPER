package android.com.viper.ui.catDetail

import android.com.viper.R
import android.com.viper.app.NetworkActivity
import android.com.viper.di.component.BaseActivityComponent
import android.com.viper.model.response.CatDetailModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_cat_detail.*
import javax.inject.Inject

class CatDetailActivity : NetworkActivity(), CatDetailRouter, CatDetailViewCallBacks {

  private val catImageId: String by lazy { intent.getStringExtra(IMAGE_ID_CAT) }
  @Inject lateinit var presenter: CatDetailPresenter
  private lateinit var catDetail: CatDetailModel

  companion object {
    private val IMAGE_ID_CAT: String = ""

    fun start(context: Context, catId: String) {
      val intent = Intent(context, CatDetailActivity::class.java)
      intent.putExtra(IMAGE_ID_CAT, catId)
      context.startActivity(intent)
    }
  }

  override fun setupComponent(component: BaseActivityComponent) = component.inject(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cat_detail)

    presenter.takeCatImageId(catImageId)

    listOf<View>(catImageView).forEach { v ->
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

  override fun showCatData(catDetails: String) {
    showIoError(catDetails)
  }

  override fun showCatImageView(catDetailModel: CatDetailModel) {
    catDetail = catDetailModel
    Glide.with(this)
      .load(catDetailModel.url)
      .diskCacheStrategy(DiskCacheStrategy.DATA)
      .into(catImageView)
  }

  private val onClickedEvent = View.OnClickListener { v ->
    when (v) {
      catImageView -> {
        presenter.catImageDetailRequested(catDetail)
      }
    }
  }
}