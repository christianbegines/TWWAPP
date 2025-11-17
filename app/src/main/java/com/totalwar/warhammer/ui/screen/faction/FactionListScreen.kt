package com.totalwar.warhammer.ui.screen.faction

import FactionErrorState
import FactionLoadingState
import FactionSuccessState
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.screen.components.CustomToolbar
import com.totalwar.warhammer.viewmodels.faction.FactionState
import com.totalwar.warhammer.viewmodels.faction.FactionViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FactionListScreen(
    viewModel: FactionViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navController: NavController,
) {
    val factionState: FactionState by viewModel.factionList.collectAsState(initial = FactionState.Idle)

    LaunchedEffect(true) {
        viewModel.findAllFactions()
    }

    val lazyGridState = rememberLazyGridState()
    val isRefreshing = factionState is FactionState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.findAllFactions() }
    )
    val appName = stringResource(id = R.string.app_name)

    Scaffold(
        topBar = {
            CustomToolbar(
                title = appName,
                openDrawer
            )
        },
        content = { padding ->
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .paint(
                        painter = painterResource(R.drawable.backgroundttw),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
                when (val state = factionState) {
                    is FactionState.Error -> FactionErrorState(
                        errorMessage = state.message,
                        onRetry = { viewModel.findAllFactions() }
                    )
                    is FactionState.Idle, is FactionState.Loading -> FactionLoadingState()
                    is FactionState.Success -> FactionSuccessState(
                        factions = state.factionList,
                        gameVersion = state.gameVersion,
                        navController = navController,
                        gridState = lazyGridState
                    )
                }
            }
        }
    )
}
