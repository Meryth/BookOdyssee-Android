package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.ui.home.HomeScreen

internal const val ROUTE_OVERVIEW: String = "overview"

internal fun NavGraphBuilder.overviewScreen(onListElementClicked: (Int) -> Unit) {
    composable(ROUTE_OVERVIEW) {
        HomeScreen(onListElementClicked = onListElementClicked)
    }
}

internal fun NavController.navigateToOverview() =
    this.navigate(ROUTE_OVERVIEW)