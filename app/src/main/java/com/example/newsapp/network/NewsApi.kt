package com.example.newsapp.network

import com.example.newsapp.models.Category
import com.example.newsapp.models.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("/v2/top-headlines/sources")
   suspend fun getSources(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
    ): Response<SourceResponse>


}