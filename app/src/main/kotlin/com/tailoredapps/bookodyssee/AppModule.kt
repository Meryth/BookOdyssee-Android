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

package com.tailoredapps.bookodyssee

import android.content.Context
import android.content.pm.ApplicationInfo
import com.tailoredapps.bookodyssee.core.model.AppBuildInfo
import com.tailoredapps.bookodyssee.ui.book.bookModule
import com.tailoredapps.bookodyssee.ui.finished.finishedModule
import com.tailoredapps.bookodyssee.ui.home.homeModule
import com.tailoredapps.bookodyssee.ui.login.loginModule
import com.tailoredapps.bookodyssee.ui.registration.registrationModule
import com.tailoredapps.bookodyssee.ui.search.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val appModule = module {
    single { provideAppBuildInfo(context = androidContext()) }
}

private fun provideAppBuildInfo(context: Context): AppBuildInfo = AppBuildInfo(
    debug = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0,
    buildType = BuildConfig.BUILD_TYPE,
    flavor = BuildConfig.FLAVOR,
    versionCode = BuildConfig.VERSION_CODE,
    versionName = BuildConfig.VERSION_NAME,
    baseUrl = BuildConfig.BASE_URL
)

internal val appModules = listOf(
    appModule,
    homeModule,
    registrationModule,
    loginModule,
    searchModule,
    bookModule,
    finishedModule,
)