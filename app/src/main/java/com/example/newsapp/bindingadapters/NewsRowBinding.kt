package com.example.newsapp.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.newsapp.R
import com.example.newsapp.fragments.NewsFragmentDirections
import com.example.newsapp.models.news.Article

class NewsRowBinding {


    companion object{


        @JvmStatic
        @BindingAdapter("onArticleClickListener")
        fun onArticleClickListener(articleRowLayout : ConstraintLayout , article: Article){
            articleRowLayout.setOnClickListener {
                try {
                    val action = NewsFragmentDirections.actionNewsFragmentToDetailsNewsFragment(article)
                    articleRowLayout.findNavController().navigate(action)
                }catch (e :Exception){
                    Log.d("onArticleClickListener" , e.message.toString())
                }
            }
        }

        @JvmStatic
        @BindingAdapter("loadImageFromUrl")
        fun loadImageFromUrl(imageView: ImageView , url:String){
            imageView.load(url){
                crossfade(600)
                error(R.drawable.ic_image_error)
            }
        }


    }



}