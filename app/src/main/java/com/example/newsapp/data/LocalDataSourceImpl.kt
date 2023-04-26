package com.example.newsapp.data

import com.example.newsapp.data.database.NewsAndSourcesDao
import com.example.newsapp.data.database.entities.NewsItemEntity
import com.example.newsapp.data.database.entities.SourceItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dao: NewsAndSourcesDao) :LocalDataSource {

    override suspend fun insertSources(sources: SourceItemEntity) {
        dao.insertSources(sources)
    }

    override suspend fun insertNews(newsItemEntity: NewsItemEntity) {
        dao.insertNews(newsItemEntity)
    }

    override fun getSourcesByCategory(category: String): Flow<List<SourceItemEntity>> {
        return dao.getSourcesByCategoryId(category)
    }

    override fun getNewsBySource(source: String):Flow<List<NewsItemEntity>> {
        return dao.getNewsBySource(source)
    }


}