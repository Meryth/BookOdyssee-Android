package com.tailoredapps.bookodyssee.core.local

import androidx.room.Entity

enum class ReadingState {
    NOT_ADDED, TO_READ, CURRENTLY_READING, FINISHED
}

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
    val readingState: ReadingState = ReadingState.TO_READ,
)

data class UpdateBook(
    val userId: Int,
    val bookId: String,
    val readingState: ReadingState
)

data class DeleteBook(
    val userId: Int,
    val bookId: String
)
