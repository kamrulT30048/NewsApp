package com.kamrulhasan.topnews.network

import com.kamrulhasan.topnews.model.NewsAPI
import com.kamrulhasan.topnews.utils.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {

    @GET(GET_GENERAL_ARTICLE)
    suspend fun getGeneralArticle() : NewsAPI

    @GET(GET_BUSINESS_ARTICLE)
    suspend fun getBusinessArticle() : NewsAPI

    @GET(GET_ENTERTAINMENT_ARTICLE)
    suspend fun getEntertainmentArticle() : NewsAPI

    @GET(GET_HEALTH_ARTICLE)
    suspend fun getHealthArticle() : NewsAPI

    @GET(GET_SPORTS_ARTICLE)
    suspend fun getSportsArticle() : NewsAPI

    @GET(GET_TECHNOLOGY_ARTICLE)
    suspend fun getTechnologyArticle() : NewsAPI

}

object NewsApi{
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}