package com.kamrulhasan.topnews.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.kamrulhasan.topnews.database.ArticleDatabase
import com.kamrulhasan.topnews.model.Article
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle
import com.kamrulhasan.topnews.network.NewsApi
import com.kamrulhasan.topnews.repository.NewsRepository
import com.kamrulhasan.topnews.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TopNewsViewModel"

class TopNewsViewModel(application: Application) : AndroidViewModel(application) {

    var allArticle: LiveData<List<LocalArticle>>
    var generalArticle: LiveData<List<LocalArticle>>
    var businessArticle: LiveData<List<LocalArticle>>
    var entertainmentArticle: LiveData<List<LocalArticle>>
    var sportsArticle: LiveData<List<LocalArticle>>
    var healthArticle: LiveData<List<LocalArticle>>
    var technologyArticle: LiveData<List<LocalArticle>>
    var bookmarkArticle: LiveData<List<BookMarkArticle>>

    private val repository: NewsRepository

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

    }

    fun realAllArticle() {
        allArticle = repository.readAllArticle
    }

    //hit all api
    fun hitServer() {
        hitGeneralArticleAPI()
        hitBusinessArticleAPI()
        hitTechnologyArticleAPI()
        hitEntertainmentArticleAPI()
        hitHealthArticleAPI()
        hitSportArticleAPI()
    }

    //hit general article api
    fun hitGeneralArticleAPI() {
        deleteArticleByCategory(GENERAL_ARTICLE)
        getGeneralArticleApi()
        readGeneralArticle()
    }

    //read general article data
    fun readGeneralArticle() {
        generalArticle = repository.readAllArticleByCategory(GENERAL_ARTICLE)
    }

    //hit business article api
    fun hitBusinessArticleAPI() {
        deleteArticleByCategory(BUSINESS_ARTICLE)
        getBusinessArticleApi()
        readBusinessArticle()
    }

    //read business article data
    fun readBusinessArticle() {
        businessArticle = repository.readAllArticleByCategory(BUSINESS_ARTICLE)
    }

    //hit sports article api
    fun hitSportArticleAPI() {
        deleteArticleByCategory(SPORTS_ARTICLE)
        getSportsArticleApi()
        readSportsArticle()
    }

    //read business article data
    fun readSportsArticle() {
        businessArticle = repository.readAllArticleByCategory(BUSINESS_ARTICLE)
    }

    //hit health article api
    fun hitHealthArticleAPI() {
        deleteArticleByCategory(HEALTH_ARTICLE)
        getHealthArticleApi()
        readHealthArticle()
    }

    //read health article data
    fun readHealthArticle() {
        healthArticle = repository.readAllArticleByCategory(HEALTH_ARTICLE)
    }

    //hit entertainment article api
    fun hitEntertainmentArticleAPI() {
        deleteArticleByCategory(ENTERTAINMENT_ARTICLE)
        getEntertainmentArticleApi()
        readEntertainmentArticle()
    }

    //read business article data
    fun readEntertainmentArticle() {
        entertainmentArticle = repository.readAllArticleByCategory(ENTERTAINMENT_ARTICLE)
    }

    //hit technology article api
    fun hitTechnologyArticleAPI() {
        deleteArticleByCategory(TECHNOLOGY_ARTICLE)
        getTechnologyArticleApi()
        readTechnologyArticle()
    }

    //read business article data
    fun readTechnologyArticle() {
        technologyArticle = repository.readAllArticleByCategory(TECHNOLOGY_ARTICLE)
    }

    fun addBookmarkArticle(bookMarkArticle: BookMarkArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addBookmarkArticle(bookMarkArticle)
            } catch (e: Exception) {
                Toast.makeText(
                    MyApplication.appContext,
                    "Database insert operation Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun updateArticle(url: String, status: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.updateArticleStatus(url, status)
            } catch (e: Exception) {
                Toast.makeText(
                    MyApplication.appContext,
                    "Database update operation Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun deleteBookmarkArticle(bookMarkArticle: BookMarkArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteBookmarkArticle(bookMarkArticle)
            } catch (e: Exception) {
                Toast.makeText(
                    MyApplication.appContext,
                    "Database delete operation Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun deleteArticleByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteAllArticle(category)
            } catch (e: Exception) {
                Toast.makeText(
                    MyApplication.appContext,
                    "Database delete operation Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getGeneralArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api article
            var articleList: List<Article>

            try {
                articleList = NewsApi.retrofitService.getGeneralArticle().articles
            } catch (e: Exception) {
                articleList = emptyList()
                Log.d(TAG, "getGeneralArticleApi: general Api call failed")
            }

            for (article in articleList) {
                val urlToImage = article.urlToImage ?: IMAGE_URL
                val localArticle = LocalArticle(
                    article.title,
                    article.author,
                    article.description,
                    urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    GENERAL_ARTICLE,
                    false
                )
                try {
                    repository.addArticle(localArticle)
                } catch (e: Exception) {
                    Toast.makeText(
                        MyApplication.appContext,
                        "Database insert operation Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getBusinessArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api article
            var articleList: List<Article>
            try {
                articleList = NewsApi.retrofitService.getBusinessArticle().articles
            } catch (e: Exception) {
                articleList = emptyList()
                Log.d(TAG, "getBusinessArticleApi: Api call failed")
            }

            for (article in articleList) {
                val urlToImage = article.urlToImage ?: IMAGE_URL
                val localArticle = LocalArticle(
                    article.title,
                    article.author,
                    article.description,
                    urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    BUSINESS_ARTICLE,
                    false
                )
                try {
                    repository.addArticle(localArticle)
                } catch (e: Exception) {
                    Toast.makeText(
                        MyApplication.appContext,
                        "Database insert operation Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getSportsArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api article
            var articleList: List<Article>
            try {
                articleList = NewsApi.retrofitService.getSportsArticle().articles
            } catch (e: Exception) {
                articleList = emptyList()
                Log.d(TAG, "getSportsArticleApi: Api call failed")
            }

            for (article in articleList) {
                val urlToImage = article.urlToImage ?: IMAGE_URL
                val localArticle = LocalArticle(
                    article.title,
                    article.author,
                    article.description,
                    urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    SPORTS_ARTICLE,
                    false
                )
                try {
                    repository.addArticle(localArticle)
                } catch (e: Exception) {
                    Toast.makeText(
                        MyApplication.appContext,
                        "Database insert operation Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getHealthArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api article
            var articleList: List<Article>
            try {
                articleList = NewsApi.retrofitService.getHealthArticle().articles
            } catch (e: Exception) {
                articleList = emptyList()
                Log.d(TAG, "getHealthArticleApi: Api call failed")
            }

            for (article in articleList) {
                val urlToImage = article.urlToImage ?: IMAGE_URL
                val localArticle = LocalArticle(
                    article.title,
                    article.author,
                    article.description,
                    urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    HEALTH_ARTICLE,
                    false
                )
                try {
                    repository.addArticle(localArticle)
                } catch (e: Exception) {
                    Toast.makeText(
                        MyApplication.appContext,
                        "Database insert operation Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getEntertainmentArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api article
            var articleList: List<Article>
            try {
                articleList = NewsApi.retrofitService.getEntertainmentArticle().articles
            } catch (e: Exception) {
                articleList = emptyList()
                Log.d(TAG, "getEntertainmentArticleApi: Api call failed")
            }

            for (article in articleList) {
                val urlToImage = article.urlToImage ?: IMAGE_URL
                val localArticle = LocalArticle(
                    article.title,
                    article.author,
                    article.description,
                    urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    ENTERTAINMENT_ARTICLE,
                    false
                )
                try {
                    repository.addArticle(localArticle)
                } catch (e: Exception) {
                    Toast.makeText(
                        MyApplication.appContext,
                        "Database insert operation Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getTechnologyArticleApi() {

        viewModelScope.launch(Dispatchers.IO) {
            //get api article
            var articleList: List<Article>
            try {
                articleList = NewsApi.retrofitService.getTechnologyArticle().articles
            } catch (e: Exception) {
                articleList = emptyList()
                Log.d(TAG, "getSportsArticleApi: Api call failed")
            }

            for (article in articleList) {
                val urlToImage = article.urlToImage ?: IMAGE_URL
                val localArticle = LocalArticle(
                    article.title,
                    article.author,
                    article.description,
                    urlToImage,
                    article.publishedAt?.let { DateConverter.zoneToDate(it) },
                    article.url,
                    TECHNOLOGY_ARTICLE,
                    false
                )
                try {
                    repository.addArticle(localArticle)
                } catch (e: Exception) {
                    Toast.makeText(
                        MyApplication.appContext,
                        "Database insert operation Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}