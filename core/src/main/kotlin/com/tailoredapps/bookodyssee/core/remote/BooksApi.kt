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

package com.tailoredapps.bookodyssee.core.remote

import com.tailoredapps.bookodyssee.core.model.BookItem
import com.tailoredapps.bookodyssee.core.model.RemoteBookList
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("/books/v1/volumes?")
    suspend fun findBookBySearchTerm(
        @Header("X-goog-api-key") apiKey: String,
        @Query("q") searchTerm: String
    ): RemoteBookList

    @GET("books/v1/volumes/{volumeId}")
    suspend fun getBookById(
        @Header("X-goog-api-key") apiKey: String,
        @Path("volumeId") bookId: String
    ) : BookItem
}