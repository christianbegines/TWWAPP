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
import com.totalwar.warhammer.ui.screen.army.ArmiesListScreen
import com.totalwar.warhammer.ui.screen.faction.FactionListScreen
import com.totalwar.warhammer.ui.screen.factionunits.UnitListScreen
import com.totalwar.warhammer.ui.screen.units.UnitScreen

private const val ID = "id"
private const val ID_PARAM = "/{${ID}}"
private const val FACTION_ID = "faction_id"
private const val FACTION_ID_PARAM = "/{${FACTION_ID}}"

@Composable
fun AppRouter(
    navController: NavHostController,
    openDrawer: () -> Unit
) {
    NavHost(navController = navController, startDestination = AppScreens.FactionsScreen.route.name) {
        composable(route = AppScreens.FactionsScreen.route.name) {
            EnterAnimation {
                FactionListScreen(
                    openDrawer = openDrawer, navController = navController
                )
            }
        }
        composable(
            route = AppScreens.FactionUnitsScreen.route.name + ID_PARAM ,
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            EnterAnimation {
                UnitListScreen(
                    navController = navController, id = it.arguments?.getString(ID).orEmpty()
                )
            }
        }
        composable(
            route = AppScreens.UnitScreen.route.name + FACTION_ID_PARAM + ID_PARAM,
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument(FACTION_ID) {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) {
            EnterAnimation {
                UnitScreen(
                    id = it.arguments?.getString(ID).orEmpty(),
                    factionId = it.arguments?.getString(FACTION_ID).orEmpty()
                )
            }
        }
        composable(route = AppScreens.FactionsScreen.route.name) {
            EnterAnimation {
                FactionListScreen(
                    openDrawer = openDrawer, navController = navController
                )
            }
        }
        composable(route = AppScreens.Account.route.name) {
        }
        composable(route = AppScreens.Contact.route.name) {
        }
        composable(route = AppScreens.Armies.route.name) {
            EnterAnimation {
                ArmiesListScreen(
                    openDrawer = openDrawer
                )
            }
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
