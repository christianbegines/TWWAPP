package com.totalwar.warhammer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(val title: String, val route: String, var icon: ImageVector) {
    object FactionsScreen : AppScreens("Faction", "factionScreen", Icons.Default.Home)
    object FactionUnitsScreen :
        AppScreens("Faction Unit Screen", "factionUnitsScreen", Icons.Default.Home)

    object UnitScreen :
        AppScreens("Faction Unit Screen", "unitScreen", Icons.Default.Home)

    object Account : AppScreens("Account", "account", Icons.Default.AccountCircle)
    object Contact : AppScreens("Raise a Concern", "contact", Icons.Default.Email)

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
