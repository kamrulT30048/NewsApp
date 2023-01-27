package com.kamrulhasan.topnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_mark_article_table")
data class BookMarkArticle(
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    @PrimaryKey
    val url: String,
    val category: String?
    )
