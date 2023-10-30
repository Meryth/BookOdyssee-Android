package com.tailoredapps.bookodyssee.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee.ui.registration.RegistrationScreen

internal const val ROUTE_REGISTRATION: String = "registration"

internal fun NavGraphBuilder.registrationScreen() {
    composable(ROUTE_REGISTRATION) {
        RegistrationScreen()
    }
}

internal fun NavController.navigateToRegistration() =
    this.navigate(ROUTE_REGISTRATION)