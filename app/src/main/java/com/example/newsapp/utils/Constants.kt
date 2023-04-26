package com.example.newsapp.utils

import com.example.newsapp.R
import com.example.newsapp.models.Category

object Constants {

     const val API_KEY  = "6177b813091f4416a7fc932ad79a65d6"
     const val BASE_URL = "https://newsapi.org"
     const val SOURCE_ITEM_TABLE = "source_item_table"
     const val NEWS_TABLE = "news_table"
     const val DATABASE_NAME = "news_database"



    val CATEGORIES_LIST = listOf(
          Category("sports", R.string.sports, R.drawable.sports, R.color.red),
          Category("general", R.string.entertainment, R.drawable.environment, R.color.blue),
          Category("health", R.string.health, R.drawable.health, R.color.pink),
          Category("science", R.string.science, R.drawable.science, R.color.yellow),
          Category("technology", R.string.technology, R.drawable.politics, R.color.blue_dark),
          Category("business", R.string.business, R.drawable.bussines, R.color.brown)
     )



}