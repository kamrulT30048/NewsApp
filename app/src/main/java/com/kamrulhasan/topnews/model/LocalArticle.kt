package com.kamrulhasan.topnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class LocalArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String?,
    val imgUrl: String?,
    val publishedDate: String,
    val newsUrl: String,
    val category: String
)
