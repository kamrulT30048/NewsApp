package com.kamrulhasan.topnews.utils

const val URL_KEY = "NEWS_URL_KEY"
const val DEFAULT_NEWS_PAGE = "https://www.bbc.com/bengali"

class Constrains {
    companion object{
        const val viewPagerCount = 3
        const val BASE_URL = "https://newsapi.org/v2/"
        //update api key at 16/01/2023
        const val API_KEY = "6c234acd217141a5b7955326bf8b2804"
        //topHeadLines
        const val getUrl_topHeadLine = "top-headlines?country=us&apiKey=$API_KEY"
    }
}