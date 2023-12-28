package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.ui.finished.FinishedScreen

internal const val ROUTE_FINISHED: String = "finished"
internal fun NavGraphBuilder.finishedScreen(
    onBookItemClick: (String) -> Unit
) {
    composable(ROUTE_FINISHED) {
        FinishedScreen(onBookItemClick = onBookItemClick)
    }
}

internal fun NavController.navigateToFinished() =
    this.navigate(ROUTE_FINISHED)
