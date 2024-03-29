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

import com.google.gson.GsonBuilder
import com.tailoredapps.bookodyssee.core.local.BookOdysseeSharedPrefs
import com.tailoredapps.bookodyssee.core.local.SharedPrefs
import com.tailoredapps.bookodyssee.core.local.localModule
import com.tailoredapps.bookodyssee.core.remote.remoteModule
import org.koin.dsl.module

internal val coreModule = module {
    single { GsonBuilder().create() }
    single<DataRepo> { CoreDataRepo(booksApi = get(), database = get()) }
    single<SharedPrefs> { BookOdysseeSharedPrefs(context = get()) }
}

val coreModules = listOf(coreModule, localModule, remoteModule)