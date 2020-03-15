package android.com.viper.app.adapter

import android.com.viper.R.layout
import android.com.viper.model.response.category.Category
import android.com.viper.app.viewHolder.CategoriesViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class CategoriesGridRecyclerAdapter(
  private val itemClickListener: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesViewHolder>() {

  private var categoryItems: MutableList<Category> = ArrayList()

  fun updateCategoryList(newCategoryItems: List<Category>) {
    categoryItems.clear()
    categoryItems.addAll(newCategoryItems)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder =
    CategoriesViewHolder(LayoutInflater.from(parent.context).inflate(layout.categories_grid_item, parent, false))

  override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
    categoryItems[position].let { holder.bindView(it, itemClickListener) }
  }

  override fun getItemCount(): Int = categoryItems.size
}