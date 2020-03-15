package android.com.viper.app.adapter

import android.com.viper.R.layout
import android.com.viper.app.viewHolder.MealsViewHolder
import android.com.viper.model.response.meals.MealModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MealsGridRecyclerAdapter(
  private val itemClickListener: (MealModel) -> Unit
) : RecyclerView.Adapter<MealsViewHolder>() {

  private var mealItems: MutableList<MealModel> = ArrayList()

  fun updateMealsList(mealModel: List<MealModel>) {
    mealItems.clear()
    mealItems.addAll(mealModel)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder =
    MealsViewHolder(LayoutInflater.from(parent.context).inflate(layout.meals_grid_item, parent, false))

  override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
    mealItems[position].let { holder.bindView(it, itemClickListener) }
  }

  override fun getItemCount(): Int = mealItems.size
}