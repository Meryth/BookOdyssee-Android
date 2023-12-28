package com.tailoredapps.bookodyssee.core.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tailoredapps.bookodyssee.core.local.LocalBook

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUser(username: String): User

    @Query("SELECT * FROM book WHERE book.userId = :userId")
    fun getSavedBooks(userId: Int): List<LocalBook>
}
