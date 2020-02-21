package android.com.viper.ui.helper

import android.content.Context
import android.view.ScaleGestureDetector
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnPinchListener(
  context: Context,
  private var recyclerView: RecyclerView
) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

  //initialize layout managers
  private val gridLayoutManager1 = GridLayoutManager(context, 1)
  private val gridLayoutManager2 = GridLayoutManager(context, 2)
  private val gridLayoutManager3 = GridLayoutManager(context, 3)
  var currentLayoutManager = gridLayoutManager3

  override fun onScale(detector: ScaleGestureDetector): Boolean {
    if (detector.currentSpan > 250 && detector.timeDelta > 250) {
      if (detector.currentSpan - detector.previousSpan < -1) {
        if (currentLayoutManager === gridLayoutManager1) {
          currentLayoutManager = gridLayoutManager2
          recyclerView.layoutManager = gridLayoutManager2
          return true
        } else if (currentLayoutManager === gridLayoutManager2) {
          currentLayoutManager = gridLayoutManager3
          recyclerView.layoutManager = gridLayoutManager3
          return true
        }
      } else if (detector.currentSpan - detector.previousSpan > 1) {
        if (currentLayoutManager === gridLayoutManager3) {
          currentLayoutManager = gridLayoutManager2
          recyclerView.layoutManager = gridLayoutManager2
          return true
        } else if (currentLayoutManager === gridLayoutManager2) {
          currentLayoutManager = gridLayoutManager1
          recyclerView.layoutManager = gridLayoutManager1
          return true
        }
      }
    }
    return false
  }
}