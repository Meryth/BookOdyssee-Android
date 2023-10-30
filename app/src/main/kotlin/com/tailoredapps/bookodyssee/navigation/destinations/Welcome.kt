package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.ui.welcome.WelcomeScreen

internal const val ROUTE_WELCOME: String = "welcome"

internal fun NavGraphBuilder.welcomeScreen() {
    composable(ROUTE_WELCOME) {
        WelcomeScreen()
    }
}

internal fun NavController.navigateToWelcome() =
    this.navigate(ROUTE_WELCOME)