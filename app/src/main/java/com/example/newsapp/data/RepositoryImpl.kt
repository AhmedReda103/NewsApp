package com.example.newsapp.data

import com.example.newsapp.data.database.entities.NewsItemEntity
import com.example.newsapp.data.database.entities.SourceItemEntity
import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.models.sources.SourceResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
    ) :Repository {
    override fun getOfflineSources(category: String): Flow<List<SourceItemEntity>> {
        return localDataSource.getSourcesByCategory(category)
    }

    override fun getOfflineNews(source: String): Flow<List<NewsItemEntity>> {
        return localDataSource.getNewsBySource(source)
    }

    override suspend fun getOnlineSources(category: String): Response<SourceResponse> {
       return remoteDataSource.getSources(category)
    }

    override suspend fun getOnlineNews(source: String): Response<NewsResponse> {
        return remoteDataSource.getNews(source)
    }

    override suspend fun insertSources(sources: SourceItemEntity) {
        localDataSource.insertSources(sources)
    }

    override suspend fun insertNews(newsItemEntity: NewsItemEntity) {
        localDataSource.insertNews(newsItemEntity)
    }


}