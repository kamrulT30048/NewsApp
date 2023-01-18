package com.kamrulhasan.topnews.repository

import androidx.lifecycle.LiveData
import com.kamrulhasan.topnews.database.ArticleDao
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle

class NewsRepository(private val articleDao: ArticleDao) {
    val readAllData: LiveData<List<LocalArticle>> = articleDao.readAllNews()

    //add one row
    suspend fun addArticle(localArticle: LocalArticle){
        articleDao.addArticle(localArticle)
    }
    suspend fun addBookmarkArticle(bookMarkArticle: BookMarkArticle){
        articleDao.addBookmark(bookMarkArticle)
    }

    suspend fun updateArticle(localArticle: LocalArticle){
        articleDao.addArticle(localArticle)
    }

    //deleting one row
    suspend fun deleteArticle(localArticle: LocalArticle){
        articleDao.deleteArticle(localArticle)
    }

    suspend fun deleteBookmarkArticle(bookMarkArticle: BookMarkArticle){
        articleDao.deleteBookMarkArticle(bookMarkArticle)
    }

    //deleting all
    suspend fun deleteAllArticle(){
        articleDao.deleteAllArticle()
    }
    suspend fun deleteAllBookmarkArticle(){
        articleDao.deleteAllBookmarkArticle()
    }
}