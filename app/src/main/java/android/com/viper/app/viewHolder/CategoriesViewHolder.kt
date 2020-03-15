package android.com.viper.app.viewHolder

import android.com.viper.model.response.category.Category
import android.com.viper.util.fitGridItemViewToScreen
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.categories_grid_item.view.*

class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bindView(item: Category, itemClickListener: (Category) -> Unit) {
    itemView.categoryImageView.fitGridItemViewToScreen(3)
    itemView.nameTextView.text = item.strCategory
    Glide.with(itemView.context)
      .load(item.strCategoryThumb)
      .diskCacheStrategy(DiskCacheStrategy.DATA)
      .into(itemView.categoryImageView)

    itemView.setOnClickListener {
      itemClickListener(item)
    }
  }
}