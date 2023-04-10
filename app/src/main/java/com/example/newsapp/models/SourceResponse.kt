package com.example.newsapp.models


import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("sources")
    val sources: List<Source?>?,
    @SerializedName("status")
    val status: String?
)