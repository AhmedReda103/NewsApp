package com.example.newsapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.MyApplication
import com.example.newsapp.data.Repository
import com.example.newsapp.data.database.entities.NewsItemEntity
import com.example.newsapp.data.database.entities.SourceItemEntity
import com.example.newsapp.models.news.NewsResponse
import com.example.newsapp.models.sources.SourceResponse
import com.example.newsapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {


    /**+
     * ROOM Database
     */

   fun readNews(sourceId : String) : LiveData<List<NewsItemEntity>>{
       return repository.getOfflineNews(sourceId).asLiveData()
   }

    fun readSources(category: String):LiveData<List<SourceItemEntity>>{
        return repository.getOfflineSources(category).asLiveData()
    }

    private fun insertNews(newsEntity: NewsItemEntity) {
        viewModelScope.launch {
            repository.insertNews(newsEntity)
        }
    }

    private fun insertSources(sourceEntity: SourceItemEntity) {
        viewModelScope.launch {
            repository.insertSources(sourceEntity)
        }
    }




    /**
     * Retrofit
     */
    private val _sources =
        MutableStateFlow<NetworkResult<SourceResponse>>(NetworkResult.Unspecified())
    val sources = _sources.asStateFlow()

    private val _news = MutableStateFlow<NetworkResult<NewsResponse>>(NetworkResult.Unspecified())
    val news = _news.asStateFlow()

    fun getNewsSources(category: String) = viewModelScope.launch {
        getNewsSourcesSafeCall(category)
    }

    fun getNewsBySource(source: String) = viewModelScope.launch {
        getNewsBySourceSafeCall(source)
    }

    private suspend fun getNewsBySourceSafeCall(source: String) {
        viewModelScope.launch { _news.emit(NetworkResult.Loading()) }
        if (hasInternetConnection()){
            try {
                val response = repository.getOnlineNews(source)
                viewModelScope.launch { _news.emit(handleNewsBySourcesResponse(response)) }
                val news = _news.value.data
                if(news!=null){
                    offlineCacheNews(news , source)
                }

            }catch (ex : Exception){
                viewModelScope.launch { _news.emit(NetworkResult.Error(ex.message.toString())) }
            }

        }else{
            viewModelScope.launch { _news.emit(NetworkResult.Error("No Internet Connection ")) }

        }
    }

    private fun offlineCacheNews(news: NewsResponse, source: String) {
        val newsEntity = NewsItemEntity(news, source)
        insertNews(newsEntity)
    }


    private suspend fun getNewsSourcesSafeCall(category: String) {
        viewModelScope.launch { _sources.emit(NetworkResult.Loading()) }
        if (hasInternetConnection()){
            try {

                val response = repository.getOnlineSources(category)
                viewModelScope.launch { _sources.emit(handleSourcesResponse(response)) }
                val sources = _sources.value.data
                if(sources!=null){
                    offlineCacheSources(sources , category)
                }
            }catch (ex : Exception){
                viewModelScope.launch { _sources.emit(NetworkResult.Error(ex.message.toString())) }
            }

        }else{
            viewModelScope.launch { _sources.emit(NetworkResult.Error("No Internet Connection ")) }

        }

    }

    private fun offlineCacheSources(sources: SourceResponse, category: String) {
        val sourceEntity = SourceItemEntity(sources , category )
        insertSources(sourceEntity)
    }



    private fun handleSourcesResponse(response: Response<SourceResponse>): NetworkResult<SourceResponse> {
        when{
            response.message().toString().contains("timeout")->{
                return NetworkResult.Error("TimeOut")
            }
            response.code()==402->{
                return NetworkResult.Error("API KEY Limited")
            }
            response.body()?.sources.isNullOrEmpty()->{
                return NetworkResult.Error("Sources Not Found")
            }
            response.isSuccessful->{
                return NetworkResult.Success(response.body()!!)
            }
            else->{
                return NetworkResult.Error(response.message().toString())
            }
        }
    }

    private fun handleNewsBySourcesResponse(response: Response<NewsResponse>): NetworkResult<NewsResponse> {
        when{
            response.message().toString().contains("timeout")->{
                return NetworkResult.Error("TimeOut")
            }
            response.code()==402->{
                return NetworkResult.Error("API KEY Limited")
            }
            response.body()?.articles.isNullOrEmpty()->{
                return NetworkResult.Error("Sources Not Found")
            }
            response.isSuccessful->{
                return NetworkResult.Success(response.body()!!)
            }
            else->{
                return NetworkResult.Error(response.message().toString())
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<MyApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?:return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?:return false
        return when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            else-> false
        }
    }


}