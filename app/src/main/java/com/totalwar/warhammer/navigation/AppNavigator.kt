package com.totalwar.warhammer.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.datastore.core.DataStore
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.viewmodels.AppViewModel
import com.totalwar.warhammer.views.FactionUnitsScreen
import com.totalwar.warhammer.views.UnitScreen
import com.totalwar.warhammer.views.faction.FactionListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppRouter(
    navController: NavHostController,
    viewModel: AppViewModel,
    dataStore: DataStore<Settings>,
    openDrawer: () -> Unit
) {
    AnimatedNavHost(navController, startDestination = AppScreens.FactionsScreen.route) {
        composable(
            route = AppScreens.FactionUnitsScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1800 }
                )
            },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { 1800 }
                )
            }
        ) {
            val id = it.arguments?.getString("id").orEmpty()
            FactionUnitsScreen(navController = navController, id = id)
        }
        composable(
            route = AppScreens.UnitScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) {
            val id = it.arguments?.getString("id").orEmpty()
            UnitScreen(
                id = id
            )
        }
        composable(route = AppScreens.FactionsScreen.route) {
            EnterAnimation {
                FactionListScreen(openDrawer = openDrawer, navController = navController)
            }
        }
        composable(route = AppScreens.Account.route) {
            // AccountScreen(navController, homeViewModel, openDrawer)
        }
        composable(route = AppScreens.Contact.route) {
            // ContactUsScreen(navController, homeViewModel, openDrawer)
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
