package com.kamrulhasan.topnews.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.kamrulhasan.topnews.database.ArticleDatabase
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.network.NewsApi
import com.kamrulhasan.topnews.repository.NewsRepository
import com.kamrulhasan.topnews.utils.USA_ARTICLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TopNewsViewModel"

class TopNewsViewModel(application: Application) : AndroidViewModel(application) {

    var allArticle : LiveData<List<LocalArticle>>
    var usaArticle : LiveData<List<LocalArticle>>
    var bookmarkArticle : LiveData<List<BookMarkArticle>>

    val repository : NewsRepository

    init {
        val article = ArticleDatabase.getDatabase(application).articleDao()
        repository = NewsRepository(article)

        allArticle = repository.readAllArticle
        bookmarkArticle = repository.readAllBookmark
        usaArticle = repository.readAllArticleByCategory(USA_ARTICLE)
//        hitServer()

//        if(allArticle.value?.isNotEmpty() == true){
//        }
    }

    //hit usa article api
    fun hitUsaArticleAPI() {
        getUsaArticleApi()
    }

    //hit all api
    fun hitServer(){
        getUsaArticleApi()
    }

    fun readArticleById(id: String): LocalArticle? {
        return repository.readArticleById(id)
    }

    fun addBookmarkArticle(bookMarkArticle: BookMarkArticle){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBookmarkArticle(bookMarkArticle)
        }
    }
    fun updateArticle(localArticle: LocalArticle){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateArticle(localArticle)
        }
    }
    fun deleteBookmarkArticle(bookMarkArticle: BookMarkArticle){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBookmarkArticle(bookMarkArticle)
        }
    }


    private fun getUsaArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {

            //get api's article
            val articleList = NewsApi.retrofitService.getTopHeadLines().articles

            try {
                repository.deleteAllArticle(USA_ARTICLE)
            } catch (e: Exception) {
                Log.d(TAG, "getHotHeadLine: table is not deleted")
            }

            for(article in articleList) {
                val localArticle = LocalArticle(
                    0,
                    article.title,
                    article.author,
                    article.description,
                    article.urlToImage,
                    article.publishedAt,
                    article.url,
                    USA_ARTICLE,
                    false
                )

                repository.addArticle(localArticle)
            }
            usaArticle = repository.readAllArticle
//            Log.d(TAG, "getHotHeadLine: ${usaArticle.value?.get(0)}")
        }
    }
}