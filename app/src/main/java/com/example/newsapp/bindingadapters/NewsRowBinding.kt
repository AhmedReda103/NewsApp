package com.example.newsapp.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.newsapp.R

class NewsRowBinding {


    companion object{

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