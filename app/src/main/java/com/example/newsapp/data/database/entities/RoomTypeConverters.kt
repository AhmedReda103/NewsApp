package com.example.newsapp.data.database.entities

import androidx.room.TypeConverter
import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.models.sources.Source
import com.example.newsapp.models.sources.SourceResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomTypeConverters {

   private val gson = Gson()

    @TypeConverter
    fun newsToString(newsResponse: NewsResponse):String{
        return gson.toJson(newsResponse)
    }

    @TypeConverter
    fun stringToNews(news:String):NewsResponse{
        val listType = object : TypeToken<NewsResponse>(){}.type
        return gson.fromJson(news , listType)
    }


    @TypeConverter
    fun sourceToString(sourceResponse: SourceResponse):String{
        return gson.toJson(sourceResponse)
    }

    @TypeConverter
    fun stringToSource(source:String):SourceResponse{
        val listType = object : TypeToken<SourceResponse>(){}.type
        return gson.fromJson(source , listType)
    }


}