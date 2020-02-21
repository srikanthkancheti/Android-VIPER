package android.com.viper.util

import android.com.viper.util.UiUtils.getVisibleHeightPercentage
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cat_images.view.*
import kotlinx.android.synthetic.main.list_item_grid.view.*

fun View.isVisible() = visibility == View.VISIBLE

fun View.setVisible(visible: Boolean) {
  visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.findRootView(): View {
  return rootView ?: this
}

fun RecyclerView.addShowCatOverlay() = addOnScrollListener(object : RecyclerView.OnScrollListener() {
  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    val layoutManager = layoutManager as GridLayoutManager
    val firstPosition = layoutManager.findFirstVisibleItemPosition()
    val lastPosition = layoutManager.findLastVisibleItemPosition()

    val globalVisibleRect = Rect()
    recyclerViewCats.getGlobalVisibleRect(globalVisibleRect)
    for (pos in firstPosition..lastPosition) {
      val view = layoutManager.findViewByPosition(pos)
      if (view != null) {
        val percentage = getVisibleHeightPercentage(view)
        if (percentage > 80.0) {
          view.imageViewCatPaw.setVisible(true)
        } else
          view.imageViewCatPaw.setVisible(false)
      }
    }
  }
})