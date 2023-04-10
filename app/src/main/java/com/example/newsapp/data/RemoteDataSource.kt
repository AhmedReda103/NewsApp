package com.example.newsapp.data

import com.example.newsapp.models.Source
import com.example.newsapp.models.SourceResponse
import com.example.newsapp.network.NewsApi
import com.example.newsapp.utils.Constants
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getSources(category : String) : Response<SourceResponse> {
        return  newsApi.getSources(Constants.API_KEY , category)
    }


}