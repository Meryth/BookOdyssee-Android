package com.tailoredapps.bookodyssee.core.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tailoredapps.bookodyssee.core.local.DeleteBook
import com.tailoredapps.bookodyssee.core.local.LocalBook

@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(book: LocalBook)

    @Delete(entity = LocalBook::class)
    suspend fun deleteBook(deleteBook: DeleteBook)

    @Query("SELECT * FROM book WHERE bookId = :bookId")
    fun getBook(bookId: String): LocalBook

    @Query("SELECT EXISTS(SELECT * FROM book WHERE userId = :userId AND bookId = :bookId)")
    fun checkIfBookExists(userId: Int, bookId: String): Boolean
}
