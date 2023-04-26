package com.example.newsapp.di

import com.example.newsapp.data.LocalDataSource
import com.example.newsapp.data.LocalDataSourceImpl
import com.example.newsapp.data.RemoteDataSource
import com.example.newsapp.data.RemoteDataSourceImpl
import com.example.newsapp.data.Repository
import com.example.newsapp.data.RepositoryImpl
import com.example.newsapp.data.database.Database
import com.example.newsapp.data.database.NewsAndSourcesDao
import com.example.newsapp.data.network.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    fun provideRemoteDataSource(webServices: NewsApi): RemoteDataSource {
        return RemoteDataSourceImpl(webServices)
    }


    @Provides
    fun provideLocalDataSource(dao: NewsAndSourcesDao): LocalDataSource {
        return LocalDataSourceImpl(dao)
    }

    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource , localDataSource: LocalDataSource):Repository{
        return RepositoryImpl(remoteDataSource,localDataSource)
    }




}