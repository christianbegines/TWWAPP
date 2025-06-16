package com.totalwar.warhammer.ui.screen.army

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.screen.army.create.CreateArmyScreen
import com.totalwar.warhammer.ui.screen.components.CustomToolbar
import com.totalwar.warhammer.viewmodels.armies.ArmiesState
import com.totalwar.warhammer.viewmodels.armies.ArmiesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArmiesListScreen(
    viewModel: ArmiesViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navController: NavController
) {
    var showCustomDialogWithResult by remember { mutableStateOf(false) }
    fun closeDialogAndRefresh() {
        showCustomDialogWithResult = !showCustomDialogWithResult
        viewModel.findAllArmies()
    }
    if (showCustomDialogWithResult) {
        CreateArmyScreen(onDismiss = {
            closeDialogAndRefresh()
        }, onNegativeClick = {
            closeDialogAndRefresh()
        }) {}
    }

    val armies by viewModel.armyState.collectAsState(
        initial = ArmiesState.Idle
    )
    val lazyGridState = rememberLazyGridState()
    LaunchedEffect(armies) {
        if (armies is ArmiesState.Idle) {
            viewModel.findAllArmies()
        }
        if (armies is ArmiesState.Success) {
            val armyList = (armies as ArmiesState.Success).armies
            if (armyList.isNotEmpty()) {
                lazyGridState.animateScrollToItem(armyList.lastIndex)
            }
        }
    }
    Scaffold(
        topBar = {
            CustomToolbar(
                title = stringResource(id = R.string.armies),
                openDrawer
            )
        },
        content = { padding ->
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(R.drawable.backgroundttw),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { showCustomDialogWithResult = true }) {
                                Text(text = "Create New Army", color = Color.White)
                            }
                        }
                    }
                    when (val state = armies) {
                        ArmiesState.Error, ArmiesState.Idle, ArmiesState.Loading -> {
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Color.White)
                                }
                            }

                        }

                        is ArmiesState.Success -> {
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pullRefresh(
                                        rememberPullRefreshState(
                                            refreshing = state is ArmiesState.Loading,
                                            onRefresh = { viewModel.findAllArmies() })
                                    )
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    state = lazyGridState
                                ) {
                                    items(state.armies) { army ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .padding(5.dp)
                                                .weight(1f, true)
                                                .paint(
                                                    painter = painterResource(R.drawable.unit_background),
                                                    contentScale = ContentScale.FillBounds
                                                )
                                        ) {
                                            if (army.flagUrl.isNotEmpty()) {
                                                Row(modifier = Modifier.padding(10.dp)) {
                                                    Image(
                                                        painter = rememberAsyncImagePainter("https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/${state.gameVersion}/${army.flagUrl}/mon_64.webp"),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(130.dp)
                                                    )
                                                }
                                            }
                                            Row(modifier = Modifier.padding(10.dp)) {
                                                Text(
                                                    text = army.name, fontSize = 15.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

            }

        }
    )
}
