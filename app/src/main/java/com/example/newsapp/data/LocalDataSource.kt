package com.example.newsapp.data

import com.example.newsapp.data.database.entities.NewsItemEntity
import com.example.newsapp.data.database.entities.SourceItemEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertSources(sources:SourceItemEntity )

    suspend fun insertNews(newsItemEntity: NewsItemEntity)

    fun getSourcesByCategory(category: String): Flow<List<SourceItemEntity>>

    fun getNewsBySource(source : String): Flow<List<NewsItemEntity>>

}