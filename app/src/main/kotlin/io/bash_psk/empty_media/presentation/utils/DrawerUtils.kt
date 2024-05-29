package io.bash_psk.empty_media.presentation.utils

import androidx.navigation.NavDestination
import io.bash_psk.empty_media.domain.map.ListData
import io.bash_psk.empty_media.domain.navigation.NavRoute

object DrawerUtils {

    fun isDrawerSelected(
        navDestination: NavDestination?,
        navRoute: NavRoute
    ) : Boolean {

        return navDestination?.route.toString() == navRoute.route
    }

    fun isDrawerOpenable(
        navDestination: NavDestination?
    ) : Boolean {

        return ListData.SCREEN_LIST.any { navRoute: NavRoute ->

            navRoute.route == navDestination?.route.toString()
        }
    }
}