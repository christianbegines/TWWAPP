package com.totalwar.warhammer.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.navigation.AppScreens
import com.totalwar.warhammer.settings.SETTINGS_DEFAULT_GAME_VERSION
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.util.CustomToolbar
import com.totalwar.warhammer.viewmodels.faction.FactionState
import com.totalwar.warhammer.viewmodels.faction.FactionViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FactionListScreen(
    viewModel: FactionViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navController: NavController,
) {
    val factionState: FactionState by viewModel.factionList.collectAsState(
        initial = FactionState.Idle
    )
    LaunchedEffect(Unit) {
        viewModel.findAllFactions()
    }
    val lazyGridState = rememberLazyGridState()
    Scaffold(
        topBar = {
            CustomToolbar(
                title = stringResource(id = R.string.app_name),
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
                when (val state = factionState) {
                    FactionState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Failed to load factions.")
                                androidx.compose.material.Button(
                                    onClick = { viewModel.findAllFactions() },
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    FactionState.Idle,
                    is FactionState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Welcome! Loading factions soon...")
                        }
                    }
                    is FactionState.Success -> {
                        val list = when (state) {
                            is FactionState.Success -> state.factionList
                            else -> emptyList()
                        }
                        val gameVersion = when (state) {
                            is FactionState.Success -> state.gameVersion
                            else -> SETTINGS_DEFAULT_GAME_VERSION
                        }
                        Box(
                            modifier = Modifier.pullRefresh(
                                rememberPullRefreshState(
                                    refreshing = factionState is FactionState.Loading,
                                    onRefresh = { viewModel.findAllFactions() }
                                )
                            )
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.padding(vertical = 4.dp),
                                state = lazyGridState
                            ) {
                                items(list) { faction ->
                                    faction?.let {
                                        FactionCard(
                                            faction = faction,
                                            navController = navController,
                                            gameVersion = gameVersion
                                        )
                                    }
                                }
                            }
                        }
                        if (state is FactionState.Loading) {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FactionCard(faction: FactionsQuery.Faction, navController: NavController, gameVersion: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .paint(
                painter = painterResource(R.drawable.unit_background),
                contentScale = ContentScale.FillBounds
            ),
        backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    navController.navigate(
                        AppScreens.FactionUnitsScreen.routeWithArgs(
                            faction.key.toString()
                        )
                    )
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter("https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/${gameVersion}/${faction.flags_url}/mon_64.webp"),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                )
            }
            Row {
                Text(
                    text = faction.subculture?.name.orEmpty(),
                    color = ColorOnPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
