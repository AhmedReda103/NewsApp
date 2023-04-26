package com.example.newsapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.database.entities.NewsItemEntity
import com.example.newsapp.data.database.entities.SourceItemEntity
import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.models.sources.SourceResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsAndSourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sourceItemEntity: SourceItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsItemEntity: NewsItemEntity)

    @Query("select * from source_item_table where category=:category ")
    fun getSourcesByCategoryId(category:String): Flow<List<SourceItemEntity>>

    @Query("select * from news_table where source=:source ")
    fun getNewsBySource(source:String):Flow<List<NewsItemEntity>>



}