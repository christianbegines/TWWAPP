package com.totalwar.warhammer.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.totalwar.warhammer.views.ArmiesListScreen
import com.totalwar.warhammer.views.FactionListScreen
import com.totalwar.warhammer.views.FactionUnitsScreen
import com.totalwar.warhammer.views.UnitScreen

@Composable
fun AppRouter(
    navController: NavHostController,
    openDrawer: () -> Unit
) {
    NavHost(navController = navController, startDestination = AppScreens.FactionsScreen.route.name) {
        composable(route = AppScreens.FactionsScreen.route.name) {
            FactionListScreen(openDrawer = openDrawer, navController = navController)
        }
        composable(
            route = AppScreens.FactionUnitsScreen.route.name + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val id = it.arguments?.getString("id").orEmpty()
            FactionUnitsScreen(navController = navController, id = id)
        }
        composable(
            route = AppScreens.UnitScreen.route.name + "/{faction_id}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("faction_id") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) {
            val id = it.arguments?.getString("id").orEmpty()
            val factionId = it.arguments?.getString("faction_id").orEmpty()
            UnitScreen(
                id = id,
                factionId = factionId
            )
        }
        composable(route = AppScreens.FactionsScreen.route.name) {
            FactionListScreen(openDrawer = openDrawer, navController = navController)
        }
        composable(route = AppScreens.Account.route.name) {
            // AccountScreen(navController, homeViewModel, openDrawer)
        }
        composable(route = AppScreens.Contact.route.name) {
            // ContactUsScreen(navController, homeViewModel, openDrawer)
        }
        composable(route = AppScreens.Armies.route.name) {
            ArmiesListScreen(openDrawer = openDrawer, navController = navController)
        }
    }
}

@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    val visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f,
            animationSpec = tween(500, 500)
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }
}
