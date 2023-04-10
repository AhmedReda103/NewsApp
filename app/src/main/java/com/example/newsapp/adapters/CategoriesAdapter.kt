package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.newsapp.R
import com.example.newsapp.databinding.LeftSidedCategoryBinding
import com.example.newsapp.databinding.RightSidedCategoryBinding
import com.example.newsapp.models.Category
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(private val categories :List<Category>)  : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewModel>() {

    val LEFT_SIDED_VIEW_TYPE = 10;
    val RIGHT_SIDED_VIEW_TYPE = 20;

    inner class CategoriesViewModel(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            val title :TextView = binding.root.findViewById(R.id.title)
            title.text = item.id
            val img :ImageView = binding.root.findViewById(R.id.image)
            img.setImageResource(item.imgResId)
            val cardView :MaterialCardView = binding.root.findViewById(R.id.material_card)
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    item.backgroundColorId
                )
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewModel {
        val view = if(viewType==LEFT_SIDED_VIEW_TYPE) {
            LeftSidedCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }else{
            RightSidedCategoryBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        }
        return CategoriesViewModel(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position%2==0) LEFT_SIDED_VIEW_TYPE else RIGHT_SIDED_VIEW_TYPE
    }


    override fun onBindViewHolder(holder: CategoriesViewModel, position: Int) {
        val category = categories[position]
        holder.bind(category)

        holder.itemView.setOnClickListener {
            onClick?.invoke(category , position)
        }
    }


    override fun getItemCount(): Int {
        return categories.size
    }

    var onClick : ((Category , Int) -> Unit)?=null


}