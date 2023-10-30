package com.tailoredapps.bookodyssee.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.tailoredapps.bookodyssee.navigation.destinations.ROUTE_MAIN
import com.tailoredapps.bookodyssee.navigation.destinations.ROUTE_WELCOME
import com.tailoredapps.bookodyssee.navigation.destinations.detailScreen
import com.tailoredapps.bookodyssee.navigation.destinations.homeScreen
import com.tailoredapps.bookodyssee.navigation.destinations.mainScreen
import com.tailoredapps.bookodyssee.navigation.destinations.navigateToDetail
import com.tailoredapps.bookodyssee.navigation.destinations.welcomeScreen

enum class NavHosts(val route: String) {
    App("nav_host_app"),
    Main("nav_host_main")
}

enum class NavGraphs(
    val route: String
) {
    Main("nav_main"),
    Welcome("nav_welcome"),
}

/**
 * App's Nav Host
 */
@Composable
fun NavHostController.AppNavHost() {
    NavHost(
        navController = this,
        route = NavHosts.App.route,
        startDestination = NavGraphs.Main.route
    ) {
        navigation(startDestination = ROUTE_MAIN, route = NavGraphs.Main.route) {
            mainScreen { navHostController ->
                navHostController.MainNavHost()
            }
        }
    }
}

/**
 * Main Nav Host
 */
@Composable
fun NavHostController.MainNavHost() {
    NavHost(
        navController = this,
        route = NavHosts.Main.route,
        startDestination = NavGraphs.Welcome.route
    ) {
        navigation(startDestination = ROUTE_WELCOME, route = NavGraphs.Welcome.route) {
            welcomeScreen()
            homeScreen(
                onListElementClicked = this@MainNavHost::navigateToDetail
            )

            detailScreen()
        }
    }
}