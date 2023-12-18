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
import com.tailoredapps.bookodyssee.core.model.BookList
import com.tailoredapps.bookodyssee.core.model.User
import com.tailoredapps.bookodyssee.core.remote.BooksApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO: put API key somewhere else
//API KEY: AIzaSyB2ERlxklfmkTeQKKpg-p1h90X3nRB7Ghw
interface DataRepo {
    suspend fun getBooksBySearchTerm(searchTerm: String): BookList

    //Database
    suspend fun getUser(username: String): User
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)

}

const val TEMP_API_KEY = "AIzaSyCB0pJ6U7O32HS2J4WogSM31LsIvVleJws"

class CoreDataRepo(
    private val booksApi: BooksApi,
    private val database: DatabaseImpl
) : DataRepo {
    override suspend fun getBooksBySearchTerm(searchTerm: String): BookList =
        booksApi.findBookBySearchTerm(apiKey = TEMP_API_KEY, searchTerm = searchTerm)

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
}