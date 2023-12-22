package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.ui.search.SearchScreen

internal const val ROUTE_SEARCH: String = "search"

internal fun NavGraphBuilder.searchScreen(onBookClick: (String) -> Unit) {
    composable(ROUTE_SEARCH) {
        SearchScreen(onBookClick = onBookClick)
    }
}

internal fun NavController.navigateToSearch() =
    this.navigate(ROUTE_SEARCH)
