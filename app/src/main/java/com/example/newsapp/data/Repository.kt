package com.example.newsapp.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor( val remoteDataSource: RemoteDataSource) {

    val remote = remoteDataSource

}