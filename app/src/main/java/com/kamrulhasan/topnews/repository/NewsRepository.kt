package com.kamrulhasan.topnews.repository

import androidx.lifecycle.LiveData
import com.kamrulhasan.topnews.database.ArticleDao
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle

class NewsRepository(private val articleDao: ArticleDao) {

    //read all local article
    val readAllArticle: LiveData<List<LocalArticle>> = articleDao.readAllArticle()

    //read all bookmark article
    val readAllBookmark: LiveData<List<BookMarkArticle>> = articleDao.readBookmarkArticle()

    //read all data by category
    fun readAllArticleByCategory(category: String): LiveData<List<LocalArticle>> {
        return articleDao.readAllArticleByCategory(category)
    }

    //add one row
    suspend fun addArticle(localArticle: LocalArticle) {
        articleDao.addArticle(localArticle)
    }

    suspend fun addBookmarkArticle(bookMarkArticle: BookMarkArticle) {
        articleDao.addBookmark(bookMarkArticle)
    }

    suspend fun updateArticleStatus(url: String, status: Boolean) {
        articleDao.updateArticleStatus(url, status)
    }

    suspend fun deleteBookmarkArticle(bookMarkArticle: BookMarkArticle) {
        articleDao.deleteBookMarkArticle(bookMarkArticle)
    }

    val bookmark = false

    //deleting all
    suspend fun deleteAllArticle(category: String) {
        articleDao.deleteAllArticleByCategory(category, bookmark)
    }
}