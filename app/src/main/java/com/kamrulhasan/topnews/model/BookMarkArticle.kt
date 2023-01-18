package com.kamrulhasan.topnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_mark_article_table")
data class BookMarkArticle(
@PrimaryKey(autoGenerate = true)
val id: Int,
val title: String,
val description: String?,
val imgUrl: String?,
val publishedDate: String,
val newsUrl: String
)
