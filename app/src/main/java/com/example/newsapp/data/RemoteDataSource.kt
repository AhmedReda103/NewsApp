package com.example.newsapp.data

import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.models.sources.SourceResponse
import retrofit2.Response

interface RemoteDataSource {


    suspend fun getSources(category : String) : Response<SourceResponse>

    suspend fun getNews(source:String): Response<NewsResponse>

}