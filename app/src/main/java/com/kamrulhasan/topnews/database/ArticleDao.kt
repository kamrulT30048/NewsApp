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
    @Query("SELECT * FROM article_table")
    fun readAllArticle() : LiveData<List<LocalArticle>>

    // read all data from particular category
    @Query("SELECT * FROM article_table WHERE category = :articleCategory")
    fun readAllArticleByCategory(articleCategory: String) : LiveData<List<LocalArticle>>

    // read article from particular id
    @Query("SELECT * FROM article_table WHERE id = :id")
    fun readArticleById(id : String) : LiveData<LocalArticle>

    /////  Read BookMark  \\\\\
    //read all bookmark article
    @Query("SELECT * FROM book_mark_article_table ORDER BY publishedAt DESC")
    fun readBookmarkArticle() : LiveData<List<BookMarkArticle>>

    @Query("UPDATE article_table SET bookmark = :status WHERE id = :id")
    suspend fun updateArticleStatus(id: Int, status: Boolean)

    @Update
    suspend fun updateLocalArticle(localArticle: LocalArticle)
//
    //fun readBookmarkArticle() : LiveData<List<BookMarkArticle>>

    @Delete
    suspend fun deleteArticle(localArticle: LocalArticle)

    @Delete
    suspend fun deleteBookMarkArticle(bookMarkArticle: BookMarkArticle)

    @Query("DELETE FROM article_table WHERE category = :articleCategory")
    suspend fun deleteAllArticle(articleCategory: String)

    @Query("DELETE FROM book_mark_article_table")
    suspend fun deleteAllBookmarkArticle()
}