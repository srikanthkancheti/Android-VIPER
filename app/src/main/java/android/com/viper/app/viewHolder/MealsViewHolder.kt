package android.com.viper.app.viewHolder

import android.com.viper.model.response.meals.MealModel
import android.com.viper.util.fitGridItemViewToScreen
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.meals_grid_item.view.*

class MealsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bindView(item: MealModel, itemClickListener: (MealModel) -> Unit) {
    itemView.mealImageView.fitGridItemViewToScreen(3)
    itemView.mealNameTextView.text = item.strMeal
    itemView.mealCategoryTextView.text = item.getMealListItemCategory()
    itemView.mealAreaTextView.text = item.getMealListItenArea()
    Glide.with(itemView.context)
      .load(item.strMealThumb)
      .diskCacheStrategy(DiskCacheStrategy.DATA)
      .into(itemView.mealImageView)

    itemView.setOnClickListener {
      itemClickListener(item)
    }
  }
}