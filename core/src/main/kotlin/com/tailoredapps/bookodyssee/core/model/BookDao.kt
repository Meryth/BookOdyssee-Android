package com.tailoredapps.bookodyssee.core.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tailoredapps.bookodyssee.core.local.LocalBook

@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(book: LocalBook)

    @Delete
    suspend fun deleteBook(book: LocalBook)

    @Query("SELECT * FROM book WHERE bookId = :bookId")
    fun getBook(bookId: String) : LocalBook
}
