package com.tailoredapps.bookodyssee.core.model

data class BookList(
    val items: List<BookItem>,
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int
)
