package com.kamrulhasan.topnews.network

import com.kamrulhasan.topnews.model.NewsAPI
import com.kamrulhasan.topnews.utils.Constrains
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
    .baseUrl(Constrains.BASE_URL)
    .build()

interface NewsApiService {

    @GET(Constrains.getUrl_topHeadLine)
    suspend fun getTopHeadLines() : NewsAPI
}

object NewsApi{
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}