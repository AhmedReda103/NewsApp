package com.example.newsapp.data

import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.models.sources.SourceResponse
import com.example.newsapp.network.NewsApi
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Constants.API_KEY
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getSources(category : String) : Response<SourceResponse> {
        return  newsApi.getSources(API_KEY , category)
    }

    suspend fun getNews(source:String):Response<NewsResponse>{
        return newsApi.getNews(API_KEY , source)
    }


}