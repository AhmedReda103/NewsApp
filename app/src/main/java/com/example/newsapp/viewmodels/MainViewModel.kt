package com.example.newsapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.MyApplication
import com.example.newsapp.data.Repository
import com.example.newsapp.models.SourceResponse
import com.example.newsapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

    private val _sources =
        MutableStateFlow<NetworkResult<SourceResponse>>(NetworkResult.Unspecified())
    val sources = _sources.asStateFlow()

    fun getNewsSources(category: String) = viewModelScope.launch {
        getNewsSourcesSafeCall(category)
    }


    private suspend fun getNewsSourcesSafeCall(category: String) {
        viewModelScope.launch { _sources.emit(NetworkResult.Loading()) }
        if (hasInternetConnection()){
            try {

                val response = repository.remote.getSources(category)
                viewModelScope.launch { _sources.emit(handleRecipesResponse(response)) }
            }catch (ex : Exception){
                viewModelScope.launch { _sources.emit(NetworkResult.Error(ex.message.toString())) }
            }

        }else{
            viewModelScope.launch { _sources.emit(NetworkResult.Error("No Internet Connection ")) }

        }

    }

    private fun handleRecipesResponse(response: Response<SourceResponse>): NetworkResult<SourceResponse> {
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