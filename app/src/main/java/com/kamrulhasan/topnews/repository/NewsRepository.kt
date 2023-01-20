package com.kamrulhasan.topnews.repository

import androidx.lifecycle.LiveData
import com.kamrulhasan.topnews.database.ArticleDao
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle

class NewsRepository(private val articleDao: ArticleDao) {
    //read all local article
    val readAllArticle: LiveData<List<LocalArticle>> = articleDao.readAllArticle()
    //read all bookmark article
    val readAllBookmark : LiveData<List<BookMarkArticle>>  = articleDao.readBookmarkArticle()

    //read all data by category
    fun readAllArticleByCategory(category: String): LiveData<List<LocalArticle>> {
        return articleDao.readAllArticleByCategory(category)
    }

    //read article by id
    fun readArticleById(id: String): LocalArticle? {
        return articleDao.readArticleById(id).value
    }

    //add one row
    suspend fun addArticle(localArticle: LocalArticle){
        articleDao.addArticle(localArticle)
    }

    suspend fun addBookmarkArticle(bookMarkArticle: BookMarkArticle){
        articleDao.addBookmark(bookMarkArticle)
    }

//    suspend fun updateArticleStatus(id:)

    suspend fun updateArticle(localArticle: LocalArticle){
        articleDao.updateLocalArticle(localArticle)
    }

    //deleting one row
    suspend fun deleteArticle(localArticle: LocalArticle){
        articleDao.deleteArticle(localArticle)
    }

    suspend fun deleteBookmarkArticle(bookMarkArticle: BookMarkArticle){
        articleDao.deleteBookMarkArticle(bookMarkArticle)
    }

    //deleting all
    suspend fun deleteAllArticle(category: String){
        articleDao.deleteAllArticle(category)
    }
    suspend fun deleteAllBookmarkArticle(){
        articleDao.deleteAllBookmarkArticle()
    }
}