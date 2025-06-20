package com.totalwar.warhammer.ui.screen.army.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.totalwar.warhammer.viewmodels.armies.ArmiesState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArmiesContent(
    state: ArmiesState,
    lazyGridState: LazyGridState,
    onRefresh: () -> Unit
) {
    when (state) {
        is ArmiesState.Loading, ArmiesState.Idle, ArmiesState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is ArmiesState.Success -> {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = false,
                onRefresh = onRefresh
            )
            Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = lazyGridState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 4.dp)
                ) {
                    items(state.armies) { army ->
                        ArmyItem(army = army, gameVersion = state.gameVersion)
                    }
                }
            }
        }
    }
}