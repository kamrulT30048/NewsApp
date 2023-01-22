package com.kamrulhasan.topnews.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.kamrulhasan.topnews.database.ArticleDatabase
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.network.NewsApi
import com.kamrulhasan.topnews.repository.NewsRepository
import com.kamrulhasan.topnews.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TopNewsViewModel"

class TopNewsViewModel(application: Application) : AndroidViewModel(application) {

    var allArticle : LiveData<List<LocalArticle>>
    var generalArticle : LiveData<List<LocalArticle>>
    var businessArticle : LiveData<List<LocalArticle>>
    var entertainmentArticle : LiveData<List<LocalArticle>>
    var sportsArticle : LiveData<List<LocalArticle>>
    var healthArticle : LiveData<List<LocalArticle>>
    var technologyArticle : LiveData<List<LocalArticle>>
    var bookmarkArticle : LiveData<List<BookMarkArticle>>

    val repository : NewsRepository

    init {
        val article = ArticleDatabase.getDatabase(application).articleDao()
        repository = NewsRepository(article)

        allArticle = repository.readAllArticle
        bookmarkArticle = repository.readAllBookmark
        generalArticle = repository.readAllArticleByCategory(GENERAL_ARTICLE)
        businessArticle = repository.readAllArticleByCategory(BUSINESS_ARTICLE)
        entertainmentArticle = repository.readAllArticleByCategory(ENTERTAINMENT_ARTICLE)
        sportsArticle = repository.readAllArticleByCategory(SPORTS_ARTICLE)
        healthArticle = repository.readAllArticleByCategory(HEALTH_ARTICLE)
        technologyArticle = repository.readAllArticleByCategory(TECHNOLOGY_ARTICLE)

//        hitServer()

    }

    //hit general article api
    fun hitGeneralArticleAPI() {
        getGeneralArticleApi()
    }
    //hit business article api
    fun hitBusinessArticleAPI() {
        getBusinessArticleApi()
    }
    //hit sports article api
    fun hitSportArticleAPI() {
        getSportsArticleApi()
    }
    //hit health article api
    fun hitHealthArticleAPI() {
        getHealthArticleApi()
    }
    //hit entertainment article api
    fun hitEntertainmentArticleAPI() {
        getEntertainmentArticleApi()
    }
    //hit technology article api
    fun hitTechnologyArticleAPI() {
        getTechnologyArticleApi()
    }

    //hit all api
    fun hitServer(){
        getGeneralArticleApi()
        getBusinessArticleApi()
        getTechnologyArticleApi()
        getEntertainmentArticleApi()
        getHealthArticleApi()
        getSportsArticleApi()
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


    private fun getGeneralArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api's article
            val articleList = NewsApi.retrofitService.getGeneralArticle().articles

            try {
                repository.deleteAllArticle(GENERAL_ARTICLE)
            } catch (e: Exception) {
                Log.d(TAG, "getHotHeadLine: table is not deleted")
            }

            for(article in articleList) {
                var bookmark = false

                val localArticle = LocalArticle(
                    0,
                    article.title,
                    article.author,
                    article.description,
                    article.urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    GENERAL_ARTICLE,
                    bookmark
                )

                repository.addArticle(localArticle)
            }
            generalArticle = repository.readAllArticleByCategory(GENERAL_ARTICLE)
        }
    }
    private fun getBusinessArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api's article
            val articleList = NewsApi.retrofitService.getBusinessArticle().articles

            try {
                repository.deleteAllArticle(BUSINESS_ARTICLE)
            } catch (e: Exception) {
                Log.d(TAG, "getHotHeadLine: table is not deleted")
            }

            for(article in articleList) {
                var bookmark = false

                val localArticle = LocalArticle(
                    0,
                    article.title,
                    article.author,
                    article.description,
                    article.urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    BUSINESS_ARTICLE,
                    bookmark
                )

                repository.addArticle(localArticle)
            }
            businessArticle = repository.readAllArticleByCategory(BUSINESS_ARTICLE)
        }
    }
    private fun getSportsArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api's article
            val articleList = NewsApi.retrofitService.getSportsArticle().articles

            try {
                repository.deleteAllArticle(SPORTS_ARTICLE)
            } catch (e: Exception) {
                Log.d(TAG, "getHotHeadLine: table is not deleted")
            }

            for(article in articleList) {
                var bookmark = false

                val localArticle = LocalArticle(
                    0,
                    article.title,
                    article.author,
                    article.description,
                    article.urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    SPORTS_ARTICLE,
                    bookmark
                )

                repository.addArticle(localArticle)
            }
            sportsArticle = repository.readAllArticleByCategory(SPORTS_ARTICLE)
        }
    }

    private fun getHealthArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api's article
            val articleList = NewsApi.retrofitService.getHealthArticle().articles

            try {
                repository.deleteAllArticle(HEALTH_ARTICLE)
            } catch (e: Exception) {
                Log.d(TAG, "getHotHeadLine: table is not deleted")
            }

            for(article in articleList) {
                var bookmark = false

                val localArticle = LocalArticle(
                    0,
                    article.title,
                    article.author,
                    article.description,
                    article.urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    HEALTH_ARTICLE,
                    bookmark
                )

                repository.addArticle(localArticle)
            }
            healthArticle = repository.readAllArticleByCategory(HEALTH_ARTICLE)
        }
    }

    private fun getEntertainmentArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api's article
            val articleList = NewsApi.retrofitService.getEntertainmentArticle().articles

            try {
                repository.deleteAllArticle(ENTERTAINMENT_ARTICLE)
            } catch (e: Exception) {
                Log.d(TAG, "getHotHeadLine: table is not deleted")
            }

            for(article in articleList) {
                var bookmark = false

                val localArticle = LocalArticle(
                    0,
                    article.title,
                    article.author,
                    article.description,
                    article.urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    ENTERTAINMENT_ARTICLE,
                    bookmark
                )

                repository.addArticle(localArticle)
            }
            entertainmentArticle = repository.readAllArticleByCategory(ENTERTAINMENT_ARTICLE)
        }
    }

    private fun getTechnologyArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api's article
            val articleList = NewsApi.retrofitService.getTechnologyArticle().articles

            try {
                repository.deleteAllArticle(TECHNOLOGY_ARTICLE)
            } catch (e: Exception) {
                Log.d(TAG, "getHotHeadLine: table is not deleted")
            }

            for(article in articleList) {
                var bookmark = false

                val localArticle = LocalArticle(
                    0,
                    article.title,
                    article.author,
                    article.description,
                    article.urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    TECHNOLOGY_ARTICLE,
                    bookmark
                )

                repository.addArticle(localArticle)
            }
            technologyArticle = repository.readAllArticleByCategory(TECHNOLOGY_ARTICLE)
        }
    }

}