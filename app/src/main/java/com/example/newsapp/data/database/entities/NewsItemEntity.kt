package com.example.newsapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.utils.Constants.NEWS_TABLE

@Entity(tableName = NEWS_TABLE)
data class NewsItemEntity(
    var newsResponse: NewsResponse ,
    var source : String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}