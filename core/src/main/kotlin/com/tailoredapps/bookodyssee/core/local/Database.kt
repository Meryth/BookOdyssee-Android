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

package com.tailoredapps.bookodyssee.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tailoredapps.bookodyssee.core.model.BookDao
import com.tailoredapps.bookodyssee.core.model.User
import com.tailoredapps.bookodyssee.core.model.UserDao

interface Database


@Database(
    entities = [User::class, LocalBook::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class)
abstract class DatabaseImpl : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object {
        const val VERSION = 1
        const val NAME = "odyssee_database"
    }
}