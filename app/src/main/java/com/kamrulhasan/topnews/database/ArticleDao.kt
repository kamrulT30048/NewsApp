package com.kamrulhasan.topnews.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.kamrulhasan.topnews.model.BookMarkArticle
import com.kamrulhasan.topnews.model.LocalArticle

@Dao
interface ArticleDao {

    @Insert(onConflict = REPLACE)
    suspend fun addArticle(localArticle: LocalArticle)
    suspend fun addBookmark(bookMarkArticle: BookMarkArticle)

    @Query("SELECT * FROM article_table")
    fun readAllNews() : LiveData<List<LocalArticle>>

    @Query("SELECT * FROM book_mark_article_table ORDER BY publishedDate DESC")
    fun readBookmarkArticle() : LiveData<List<BookMarkArticle>>

    @Delete
    suspend fun deleteArticle(localArticle: LocalArticle)

    @Delete
    suspend fun deleteBookMarkArticle(bookMarkArticle: BookMarkArticle)

    @Query("DELETE FROM article_table")
    suspend fun deleteAllArticle()

    @Query("DELETE FROM book_mark_article_table")
    suspend fun deleteAllBookmarkArticle()
}