package com.kamrulhasan.topnews.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

//Web View Fragment
const val URL_KEY = "NEWS_URL_KEY"
const val DEFAULT_NEWS_PAGE = "https://www.bbc.com/bengali"

//Details key
const val ARTICLE_ID_KEY = "ArticleId"

//Fragment category key
const val GENERAL_ARTICLE = "general"
const val BUSINESS_ARTICLE = "business"
const val ENTERTAINMENT_ARTICLE = "entertainment"
const val SPORTS_ARTICLE = "sports"
const val HEALTH_ARTICLE = "health"
const val TECHNOLOGY_ARTICLE = "technology"

const val VIEW_PAGER_COUNTER = 7

////  API url  \\\\
const val BASE_URL = "https://newsapi.org/v2/"
//update api key at 16/01/2023
const val API_KEY = "6c234acd217141a5b7955326bf8b2804"

//GET GENERAL Article API
const val GET_GENERAL_ARTICLE = "top-headlines?category=general&apiKey=$API_KEY"
//GET Business API
const val GET_BUSINESS_ARTICLE = "top-headlines?category=business&apiKey=$API_KEY"
//GET Entertainment API
const val GET_ENTERTAINMENT_ARTICLE = "top-headlines?category=entertainment&apiKey=$API_KEY"
//GET Health API
const val GET_HEALTH_ARTICLE = "top-headlines?category=health&apiKey=$API_KEY"
//GET Sports API
const val GET_SPORTS_ARTICLE = "top-headlines?category=sports&apiKey=$API_KEY"
//GET Technology API
const val GET_TECHNOLOGY_ARTICLE = "top-headlines?category=technology&apiKey=$API_KEY"


// internet connectivity error massage
const val CHECK_INTERNET = "Please check your internet connection to reload!!"

class Constrains {
    companion object{
        //companion object
//        val CONTEXT = Application().baseContext //AppCompatActivity()

    }
}

fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}