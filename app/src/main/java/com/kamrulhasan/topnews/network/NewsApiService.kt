package com.kamrulhasan.topnews.network

import com.kamrulhasan.topnews.model.NewsAPI
import com.kamrulhasan.topnews.utils.BASE_URL
import com.kamrulhasan.topnews.utils.GET_USA_ARTICLE
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

    @GET(GET_USA_ARTICLE)
    suspend fun getTopHeadLines() : NewsAPI
}

object NewsApi{
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}