package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.ui.home.HomeScreen

internal const val ROUTE_HOME: String = "home"

internal fun NavGraphBuilder.homeScreen(
    onBookItemClick: (String) -> Unit,
    onAddClick: () -> Unit,
    onFinishedClick: () -> Unit
) {
    composable(ROUTE_HOME) {
        HomeScreen(
            onBookItemClick = onBookItemClick,
            onAddClick = onAddClick,
            onFinishedClick = onFinishedClick
        )
    }
}

internal fun NavController.navigateToHome() =
    this.navigate(ROUTE_HOME)