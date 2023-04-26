package com.example.newsapp.data

import com.example.newsapp.data.database.entities.NewsItemEntity
import com.example.newsapp.data.database.entities.SourceItemEntity
import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.models.sources.SourceResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {


     fun getOfflineSources(category: String) :Flow<List<SourceItemEntity>>

     fun getOfflineNews(source:String) : Flow<List<NewsItemEntity>>

     suspend fun getOnlineSources(category: String):  Response<SourceResponse>

     suspend fun getOnlineNews(source: String) : Response<NewsResponse>

     suspend fun insertSources(sources:SourceItemEntity )

     suspend fun insertNews(newsItemEntity: NewsItemEntity)



}