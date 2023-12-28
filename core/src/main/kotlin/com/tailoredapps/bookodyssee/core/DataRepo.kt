/*
 * Copyright 2020 Tailored Media GmbH.
 * Created by Florian Schuster.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tailoredapps.bookodyssee.core

import com.tailoredapps.bookodyssee.core.local.DatabaseImpl
import com.tailoredapps.bookodyssee.core.local.DeleteBook
import com.tailoredapps.bookodyssee.core.local.LocalBook
import com.tailoredapps.bookodyssee.core.local.ReadingState
import com.tailoredapps.bookodyssee.core.local.UpdateBook
import com.tailoredapps.bookodyssee.core.model.BookItem
import com.tailoredapps.bookodyssee.core.model.RemoteBookList
import com.tailoredapps.bookodyssee.core.model.User
import com.tailoredapps.bookodyssee.core.remote.BooksApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO: put API key somewhere else
//API KEY: AIzaSyB2ERlxklfmkTeQKKpg-p1h90X3nRB7Ghw
interface DataRepo {
    suspend fun getBooksBySearchTerm(searchTerm: String): RemoteBookList
    suspend fun getBookById(id: String): BookItem

    // DATABASE
    suspend fun getUser(username: String): User
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun getUserBookList(userId: Int): List<LocalBook>
    suspend fun getBookByUser(userId: Int, bookId: String): LocalBook?
    suspend fun insertBook(book: LocalBook)
    suspend fun updateBook(userId: Int, bookId: String, readingState: ReadingState)
    suspend fun deleteBook(userId: Int, bookId: String)
}

const val TEMP_API_KEY = "AIzaSyCB0pJ6U7O32HS2J4WogSM31LsIvVleJws"

class CoreDataRepo(
    private val booksApi: BooksApi,
    private val database: DatabaseImpl
) : DataRepo {
    override suspend fun getBooksBySearchTerm(searchTerm: String): RemoteBookList =
        booksApi.findBookBySearchTerm(apiKey = TEMP_API_KEY, searchTerm = searchTerm)

    override suspend fun getBookById(id: String): BookItem =
        booksApi.getBookById(apiKey = TEMP_API_KEY, bookId = id)


    // DATABASE
    override suspend fun getUser(username: String): User =
        withContext(Dispatchers.IO) {
            database.userDao().getUser(username)
        }

    override suspend fun insertUser(user: User) =
        withContext(Dispatchers.IO) {
            database.userDao().insertUser(user)
        }

    override suspend fun updateUser(user: User) =
        withContext(Dispatchers.IO) {
            database.userDao().updateUser(user)
        }

    override suspend fun getUserBookList(userId: Int): List<LocalBook> =
        withContext(Dispatchers.IO) {
            database.userDao().getSavedBooks(userId)
        }

    override suspend fun getBookByUser(userId: Int, bookId: String): LocalBook? =
        withContext(Dispatchers.IO) {
            database.bookDao().getBookByUser(userId, bookId)
        }

    override suspend fun insertBook(book: LocalBook) =
        withContext(Dispatchers.IO) {
            database.bookDao().insertBook(book)
        }

    override suspend fun updateBook(userId: Int, bookId: String, readingState: ReadingState) =
        withContext(Dispatchers.IO) {
            database.bookDao().updateReadingState(
                UpdateBook(
                    userId = userId,
                    bookId = bookId,
                    readingState = readingState
                )
            )
        }

    override suspend fun deleteBook(userId: Int, bookId: String) =
        withContext(Dispatchers.IO) {
            database.bookDao().deleteBook(
                deleteBook = DeleteBook(
                    userId = userId,
                    bookId = bookId
                )
            )
        }
}
