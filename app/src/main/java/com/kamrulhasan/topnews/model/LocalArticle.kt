package com.kamrulhasan.topnews.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "article_table")
data class LocalArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val url: String?,
    val category: String?,
    val bookmark: Boolean
):Parcelable