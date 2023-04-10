package com.example.newsapp.models.sources


import com.example.newsapp.models.sources.Source
import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("sources")
    val sources: List<Source?>?,
    @SerializedName("status")
    val status: String?
)