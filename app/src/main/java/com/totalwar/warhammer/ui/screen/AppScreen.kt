package com.totalwar.warhammer.ui.screen

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.totalwar.warhammer.navigation.AppRouter
import com.totalwar.warhammer.ui.screen.components.Drawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        AppDrawer(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
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
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
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
