package com.totalwar.warhammer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
enum class ScreenRoute {
    FactionsScreen,
    FactionUnitsScreen,
    UnitScreen,
    Account,
    Contact,
    Armies
}

sealed class AppScreens(val title: String, val route: ScreenRoute, var icon: ImageVector) {
    data object FactionsScreen : AppScreens("Faction", ScreenRoute.FactionsScreen, Icons.Default.Home)
    object FactionUnitsScreen :
        AppScreens("Faction Unit Screen", ScreenRoute.FactionUnitsScreen, Icons.Default.Home)

    object UnitScreen :
        AppScreens("Unit Screen", ScreenRoute.UnitScreen, Icons.Default.Home)

    object Account : AppScreens("Account", ScreenRoute.Account, Icons.Default.AccountCircle)
    object Contact : AppScreens("Raise a Concern", ScreenRoute.Contact, Icons.Default.Email)

    object Armies : AppScreens("Armies", ScreenRoute.Armies, Icons.Default.Build )

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
