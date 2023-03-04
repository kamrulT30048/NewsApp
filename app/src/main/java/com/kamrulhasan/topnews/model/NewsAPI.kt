package com.kamrulhasan.topnews.model

data class NewsAPI(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
)