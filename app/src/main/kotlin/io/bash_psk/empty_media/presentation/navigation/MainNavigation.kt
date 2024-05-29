package io.bash_psk.empty_media.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.bash_psk.empty_media.domain.navigation.NavRoute
import io.bash_psk.empty_media.presentation.activities.main.MainViewModel
import io.bash_psk.empty_media.presentation.screen.CommandsScreen
import io.bash_psk.empty_media.presentation.screen.DownloadsScreen
import io.bash_psk.empty_media.presentation.screen.HomeScreen
import io.bash_psk.empty_media.presentation.screen.SettingsScreen

@Composable
fun MainNavigation(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    isDrawerOpen: Boolean,
    onDrawerMenu: (
        isOpen: Boolean
    ) -> Unit
) {

    NavHost(
        navController = navHostController,
        startDestination = NavRoute.Home.route
    ) {

        composable(
            route = NavRoute.Home.route
        ) { navBackStackEntry: NavBackStackEntry ->

            HomeScreen(
                mainViewModel = mainViewModel,
                isDrawerOpen = isDrawerOpen,
                onDrawerMenu = onDrawerMenu,
                onNavigateScreen = { navRoute: NavRoute ->

                    navHostController.navigate(
                        route = navRoute.route
                    ) {

                        popUpTo(
                            route = navHostController.graph.startDestinationRoute.toString()
                        ) {

                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(
            route = NavRoute.Downloads.route
        ) { navBackStackEntry: NavBackStackEntry ->

            DownloadsScreen(
                mainViewModel = mainViewModel,
                isDrawerOpen = isDrawerOpen,
                onDrawerMenu = onDrawerMenu,
                onNavigateScreen = { navRoute: NavRoute ->

                    navHostController.navigate(
                        route = navRoute.route
                    ) {

                        popUpTo(
                            route = navHostController.graph.startDestinationRoute.toString()
                        ) {

                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(
            route = NavRoute.Commands.route
        ) { navBackStackEntry: NavBackStackEntry ->

            CommandsScreen(
                mainViewModel = mainViewModel,
                isDrawerOpen = isDrawerOpen,
                onDrawerMenu = onDrawerMenu,
                onNavigateScreen = { navRoute: NavRoute ->

                    navHostController.navigate(
                        route = navRoute.route
                    ) {

                        popUpTo(
                            route = navHostController.graph.startDestinationRoute.toString()
                        ) {

                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(
            route = NavRoute.Settings.route
        ) { navBackStackEntry: NavBackStackEntry ->

            SettingsScreen(
                mainViewModel = mainViewModel,
                isDrawerOpen = isDrawerOpen,
                onDrawerMenu = onDrawerMenu,
                onNavigateScreen = { navRoute: NavRoute ->

                    navHostController.navigate(
                        route = navRoute.route
                    ) {

                        popUpTo(
                            route = navHostController.graph.startDestinationRoute.toString()
                        ) {

                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}