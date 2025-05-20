package com.totalwar.warhammer.ui.screen

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.totalwar.warhammer.navigation.AppRouter
import com.totalwar.warhammer.util.Drawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AppScreen() {
    Surface(color = MaterialTheme.colors.background) {
        AppDrawer(
            drawerState = rememberDrawerState(DrawerValue.Closed),
            navController = rememberNavController(),
            scope = rememberCoroutineScope()
        )
    }
}

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    navController: NavHostController,
    scope: CoroutineScope
) {
    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Drawer(
                onDestinationClicked = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        navController.graph.startDestinationRoute?.let { start ->
                            popUpTo(start) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        AppRouter(
            navController = navController,
            openDrawer = {
                scope.launch { drawerState.open() }
            }
        )
    }
}