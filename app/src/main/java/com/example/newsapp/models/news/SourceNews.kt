package com.example.newsapp.models.news


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceNews(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
):Parcelable