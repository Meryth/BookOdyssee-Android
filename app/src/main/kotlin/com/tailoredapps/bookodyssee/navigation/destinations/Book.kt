package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.navigation.getIntArgOrNull
import com.tailoredapps.bookodyssee.ui.book.BookScreen

internal const val ROUTE_BOOK_WITH_ARGS: String = "book/{id}"

internal fun NavGraphBuilder.bookScreen() {
    composable(ROUTE_BOOK_WITH_ARGS) { navBackStackEntry ->
        BookScreen(id = navBackStackEntry.getIntArgOrNull("id"))
    }
}

internal fun NavController.navigateToBook(id: Int) =
    this.navigate(ROUTE_BOOK_WITH_ARGS.replace("{id}", id.toString()))

