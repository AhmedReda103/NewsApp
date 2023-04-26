package com.example.newsapp.data.database

import androidx.room.*
import androidx.room.Database
import com.example.newsapp.data.database.entities.NewsItemEntity
import com.example.newsapp.data.database.entities.RoomTypeConverters
import com.example.newsapp.data.database.entities.SourceItemEntity

@Database(entities = [SourceItemEntity::class , NewsItemEntity::class] , version = 1 , exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class Database : RoomDatabase() {

    abstract fun dao() : NewsAndSourcesDao


}