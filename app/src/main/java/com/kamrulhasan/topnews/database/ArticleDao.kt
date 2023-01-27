package com.kamrulhasan.topnews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query
import androidx.room.Update
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle

@Dao
interface ArticleDao {

    @Insert(onConflict = IGNORE)
    suspend fun addArticle(localArticle: LocalArticle)

    @Insert(onConflict = IGNORE)
    suspend fun addBookmark(bookMarkArticle: BookMarkArticle)

    ////  Read Article  \\\\
    // read all data from local article
    @Query("SELECT * FROM article_table ORDER BY publishedAt DESC")
    fun readAllArticle(): LiveData<List<LocalArticle>>

    // read all data from particular category
    @Query("SELECT * FROM article_table WHERE category = :articleCategory ORDER BY publishedAt DESC")
    fun readAllArticleByCategory(articleCategory: String): LiveData<List<LocalArticle>>

    /////  Read BookMark  \\\\\
    //read all bookmark article
    @Query("SELECT * FROM book_mark_article_table ORDER BY publishedAt DESC")
    fun readBookmarkArticle(): LiveData<List<BookMarkArticle>>

    //read all bookmark article
    @Query("SELECT * FROM book_mark_article_table WHERE url = :url")
    fun readBookmarkArticleByUrl(url: String): BookMarkArticle

    @Query("UPDATE article_table SET bookmark = :status WHERE url = :url")
    suspend fun updateArticleStatus(url: String, status: Boolean)

    @Update
    suspend fun updateLocalArticle(localArticle: LocalArticle)

    @Delete
    suspend fun deleteArticle(localArticle: LocalArticle)

    @Delete
    suspend fun deleteBookMarkArticle(bookMarkArticle: BookMarkArticle)

    @Query("DELETE FROM article_table WHERE category = :articleCategory AND bookmark = :notBookmark")
    suspend fun deleteAllArticleByCategory(articleCategory: String, notBookmark: Boolean)

    @Query("DELETE FROM article_table WHERE bookmark = :notBookmark")
    suspend fun deleteAllArticle(notBookmark: Boolean)

    @Query("DELETE FROM book_mark_article_table")
    suspend fun deleteAllBookmarkArticle()
}