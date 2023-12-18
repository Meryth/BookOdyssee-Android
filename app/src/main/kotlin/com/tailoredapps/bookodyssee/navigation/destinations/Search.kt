package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.ui.search.SearchScreen

internal const val ROUTE_SEARCH: String = "Search"

internal fun NavGraphBuilder.searchScreen() {
    composable(ROUTE_SEARCH) {
        SearchScreen()
    }
}

internal fun NavController.navigateToSearch() =
    this.navigate(ROUTE_SEARCH)
