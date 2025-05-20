package com.totalwar.warhammer.ui.screen.factionunits

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.screen.factionunits.composables.UnitListCard
import com.totalwar.warhammer.util.CustomToolbarWithBackArrow
import com.totalwar.warhammer.viewmodels.factionunits.FactionUnitsState
import com.totalwar.warhammer.viewmodels.factionunits.FactionUnitsViewModel
import com.totalwar.warhammer.ui.screen.components.header.Header

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnitListScreen(
    navController: NavHostController, viewModel: FactionUnitsViewModel = hiltViewModel(), id: String
) {
    val faction by viewModel.faction.collectAsState(initial = FactionUnitsState.Idle)
    val factionState = faction
    LaunchedEffect(id) {
        viewModel.findUnitsByFaction(id)
    }

    Scaffold(topBar = {
        val title = when (factionState) {
            is FactionUnitsState.Success -> factionState.faction.subculture?.name.orEmpty()
            else -> ""
        }
        CustomToolbarWithBackArrow(title = "$title Units", navController = navController)
    }, content = {
        Surface(
            color = Color.Transparent, modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.backgroundttw),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            when (val state = faction) {
                is FactionUnitsState.Error -> {}
                is FactionUnitsState.Idle, is FactionUnitsState.Loading -> {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is FactionUnitsState.Success -> {
                    val list = state.unitTypes
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        list.forEach { (initial, units) ->
                            stickyHeader {
                                Header(initial)
                            }
                            items(units) { units ->
                                UnitListCard(
                                    state.faction.key.orEmpty(), units, state.gameVersion
                                )
                            }
                        }
                    }
                }
            }
        }
    })
}
