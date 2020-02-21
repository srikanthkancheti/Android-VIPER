package android.com.viper.util

import android.com.viper.R
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

object UiUtils {

  @JvmStatic
  fun showWhiteSnackbar(view: View?, text: CharSequence) {
    createWhiteSnackBar(view, text)?.show()
  }

  private fun createWhiteSnackBar(view: View?, text: CharSequence): Snackbar? {
    view?.let {
      val snack = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
      val textView = snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
      textView.setTextColor(Color.WHITE)
      textView.maxLines = 3
      // textView.typeface = TypefaceManager.getInstance(textView.context).getTypeface(
      //   TypefaceManager.TYPEFACE_CODE_FIRA_SANS_REGULAR)
      snack.view.setBackgroundResource(R.color.grey_5f6673)
      return snack
    } ?: return null
  }

  //Method to calculate how much of the view is visible
  fun getVisibleHeightPercentage(view: View): Double {

    val itemRect = Rect()
    val isParentViewEmpty = view.getLocalVisibleRect(itemRect)

    // Find the height of the item.
    val visibleHeight = itemRect.height().toDouble()
    val height = view.measuredHeight

    val viewVisibleHeightPercentage = visibleHeight / height * 100

    if(isParentViewEmpty){
      return viewVisibleHeightPercentage
    }else{
      return 0.0
    }
  }
}