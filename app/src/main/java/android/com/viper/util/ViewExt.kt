package android.com.viper.util

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView

fun View.isVisible() = visibility == View.VISIBLE

fun View.setVisible(visible: Boolean) {
  visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.findRootView(): View {
  return rootView ?: this
}

fun View.fitGridItemViewToScreen(numberOfColumns: Int) {
  val context = this.context
  // Setting item view to fit for different screen sizes
  val displayMetrics = DisplayMetrics()
  (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
  val width = displayMetrics.widthPixels
  this.layoutParams.height = width / numberOfColumns
}