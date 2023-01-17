package com.kamrulhasan.topnews.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamrulhasan.topnews.model.Article
import com.kamrulhasan.topnews.network.NewsApi
import kotlinx.coroutines.launch

private const val TAG = "TopNewsViewModel"

class TopNewsViewModel : ViewModel() {

//    private val _jsonObject = MutableLiveData<News>()
//    val jsonObject : LiveData<News> = _jsonObject

    private val _jsonArray = MutableLiveData<List<Article>>()
    val jsonArray : LiveData<List<Article>> = _jsonArray

    init {
        hitServer()
    }

    fun hitServer(){
        getHotHeadLine()
    }

    private fun getHotHeadLine() {
        viewModelScope.launch {
            try{
                val listHotHeadLines = NewsApi.retrofitService.getTopHeadLines()
                _jsonArray.value = listHotHeadLines.articles
//                Log.d(TAG, "getHotHeadLine(DATA): $")
                Log.d(TAG, "getHotHeadLine(DATA_list): ${listHotHeadLines.articles[0]}")
                Log.d(TAG, "getHotHeadLine(DATA_list): ${listHotHeadLines.articles[3]}")
                Log.d(TAG, "getHotHeadLine(jsonArray_list): ${jsonArray.value}")
            }catch (e: Exception){
                Log.d(TAG, "getHotHeadLine: $e")
            }
        }
    }
}