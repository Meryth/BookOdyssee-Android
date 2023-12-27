package com.tailoredapps.bookodyssee.core.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "book",
    primaryKeys = ["userId", "bookId"]
)
data class LocalBook(
    val userId: Int,
    val bookId: String,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val pageCount: Int,
    val imageLink: String,
    val readState: String, //TODO: think about how to best save state
)

data class DeleteBook(
    val userId: Int,
    val bookId: String
)
