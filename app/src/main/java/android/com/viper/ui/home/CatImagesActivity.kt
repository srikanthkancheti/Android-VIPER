package android.com.viper.ui.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.com.viper.R
import android.com.viper.app.NetworkActivity
import android.com.viper.di.component.BaseActivityComponent
import android.com.viper.model.response.CatImagesModel
import android.com.viper.ui.catDetail.CatDetailActivity
import android.com.viper.ui.helper.OnBottomReachedListener
import android.com.viper.ui.helper.OnPinchListener
import android.com.viper.util.addShowCatOverlay
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_cat_images.*
import kotlinx.android.synthetic.main.list_item_grid.view.*
import javax.inject.Inject

class CatImagesActivity : NetworkActivity(), CatImagesViewCallBacks, CatImagesRouter, OnBottomReachedListener {

  @Inject lateinit var presenter: CatImagesPresenter
  private var adapter: CatsListGridRecyclerAdapter? = null
  private var scaleGestureDetector: ScaleGestureDetector? = null

  override fun setupComponent(component: BaseActivityComponent) = component.inject(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cat_images)
    initView()
    presenter.subscribeToCatImageUpdates()
  }

  /**
   * False = to enable taking screenshots
   * True = to disable taking screenshots
   */
  override fun isSecureScreen(): Boolean {
    return false
  }

  private fun initView() {

    recyclerViewCats.layoutManager = GridLayoutManager(this, 3)
    adapter = CatsListGridRecyclerAdapter( this, this) { catImagesModel -> presenter.catImageDetailRequested(catImagesModel) }
    recyclerViewCats.adapter = adapter

    // Adding overlay whenever >80% of the image height is visible
    recyclerViewCats.addShowCatOverlay()

    //set scale gesture detector for pinch to zoom
    if (scaleGestureDetector == null) {
      val onPinchListener = OnPinchListener(this, recyclerViewCats)
      scaleGestureDetector = ScaleGestureDetector(this, onPinchListener)
    }

    //set touch listener on recycler view
    recyclerViewCats.setOnTouchListener { _, event ->
      scaleGestureDetector!!.onTouchEvent(event)
      false
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

  /**
   * Start of View callbacks region
   */
  // View call back method to bing data to recycler view
  override fun catImagesCollected(catImages: PagedList<CatImagesModel>) {
    adapter?.submitList(catImages)
  }

  override fun showCatImagesEmpty() {
    showIoError("You reached to the end of the list, nothing more to load!")
  }

  override fun showCatImagesError() {
    showDefaultErrorMessage()
  }

  // Router method implementation to navigate to detail screen
  override fun openCatImageDetailView(catImagesModel: CatImagesModel) {
    CatDetailActivity.start(this, catImagesModel.id)
  }

  /**
   * End of view callbacks region
   */

  // Call API scroll position reached to last item
  override fun onBottomReached() {
    presenter.requestCatImages()
  }

  // Adapter to render grid items
  class CatsListGridRecyclerAdapter(
    private val context: Context,
    private val onBottomReachedListener: OnBottomReachedListener,
    private val itemClickListener: (CatImagesModel) -> Unit
  ) : PagedListAdapter<CatImagesModel, ViewHolder>(IMAGES_COMPARATOR) {

    private var listOfCatImages: PagedList<CatImagesModel>? = null

    override fun submitList(pagedList: PagedList<CatImagesModel>?) {
      listOfCatImages = pagedList
      super.submitList(pagedList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return CatsListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_grid, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
      val catsListViewHolder = viewHolder as CatsListViewHolder
      listOfCatImages?.get(position)?.let { catsListViewHolder.bindView(it, itemClickListener) }

      if (position == (listOfCatImages?.size)?.minus(1)) {
        onBottomReachedListener.onBottomReached()
      }
    }

    override fun onViewRecycled(holder: ViewHolder) {
      if (holder is CatsListViewHolder) {
        holder.cancelImageLoading()
      }
    }

    companion object {
      val IMAGES_COMPARATOR = object : DiffUtil.ItemCallback<CatImagesModel>() {
        override fun areItemsTheSame(oldItem: CatImagesModel, newItem: CatImagesModel): Boolean =
          oldItem != newItem

        override fun areContentsTheSame(oldItem: CatImagesModel, newItem: CatImagesModel): Boolean =
          oldItem.id != newItem.id
      }
    }
  }
}

class CatsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bindView(item: CatImagesModel, itemClickListener: (CatImagesModel) -> Unit) {
    val context = itemView.context
    // Setting item view to fit for different screen sizes
    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    itemView.imageViewCat.layoutParams.height = width / 3
    itemView.imageViewCatPaw.layoutParams.height = width / 3

    ObjectAnimator.ofFloat(itemView.imageViewCatPaw, View.ALPHA, 0.2f, 1.0f).setDuration(1000).start();

    Glide.with(context)
      .load(item.url)
      .diskCacheStrategy(DiskCacheStrategy.DATA)
      .into(itemView.imageViewCat)

    itemView.setOnClickListener {
      itemClickListener(item)
    }
  }

  fun cancelImageLoading() {
    Glide.with(itemView.context).clear(itemView)
  }
}
