package com.example.newsapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.models.sources.SourceResponse
import com.example.newsapp.utils.Constants.SOURCE_ITEM_TABLE

@Entity(tableName = SOURCE_ITEM_TABLE)
data class SourceItemEntity(

    var source: SourceResponse ,
    var category : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int =0
}