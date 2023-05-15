package com.totalwar.warhammer.views.faction

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
import androidx.compose.foundation.lazy.grid.LazyGridState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.navigation.AppScreens
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.util.CustomToolbar
import com.totalwar.warhammer.viewmodels.faction.FactionState
import com.totalwar.warhammer.viewmodels.faction.FactionViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FactionListScreen(
    viewModel: FactionViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navController: NavController
) {
    val factionList: FactionState by viewModel.factionList.observeAsState(
        initial = FactionState.Idle,
    )
    viewModel.findAllFactions()

    val lazyGridState = rememberLazyGridState()
    Scaffold(
        topBar = {
            CustomToolbar(
                title = stringResource(id = R.string.app_name),
                openDrawer,
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
                        contentScale = ContentScale.FillBounds,
                    ),
            ) {
                when (val state = factionList) {
                    FactionState.Error -> {}
                    FactionState.Idle -> {}
                    is FactionState.Loading,
                    is FactionState.Success -> {
                        val list = when (state) {
                            is FactionState.Loading -> state.factionList
                            is FactionState.Success -> state.factionList
                            else -> emptyList()
                        }
                        Box(
                            modifier = Modifier.pullRefresh(
                                rememberPullRefreshState(
                                    refreshing = factionList is FactionState.Loading,
                                    onRefresh = { viewModel.findAllFactions() },
                                ),
                            ),
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.padding(vertical = 4.dp),
                                state = lazyGridState,
                            ) {
                                items(list) { faction ->
                                    faction?.let {
                                        FactionCard(
                                            faction = faction,
                                            navController = navController,
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
        },
    )
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun FactionCard(faction: FactionsQuery.Faction, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .paint(
                painter = painterResource(R.drawable.unit_background),
                contentScale = ContentScale.FillBounds,
            ),
        backgroundColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    navController.navigate(
                        AppScreens.FactionUnitsScreen.routeWithArgs(
                            faction.key.toString(),
                        ),
                    )
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow,
                    ),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter("https://res.cloudinary.com/fishofstone/image/upload/w_64,f_auto/twwstats/api/327635228256759215/${faction.flags_url}/mon_64.jpg"),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp),
                )
            }
            Row {
                Text(
                    text = faction.subculture?.name.orEmpty(),
                    color = ColorOnPrimary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
