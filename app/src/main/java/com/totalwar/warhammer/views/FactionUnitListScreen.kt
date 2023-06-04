package com.totalwar.warhammer.views

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.util.CustomToolbarWithBackArrow
import com.totalwar.warhammer.util.isLargeUnit
import com.totalwar.warhammer.util.map
import com.totalwar.warhammer.viewmodels.factionunits.FactionUnitsState
import com.totalwar.warhammer.viewmodels.factionunits.FactionUnitsViewModel
import com.totalwar.warhammer.views.common.UnitAbility
import com.totalwar.warhammer.views.common.UnitAttribute
import com.totalwar.warhammer.views.common.UnitIconImage
import com.totalwar.warhammer.views.common.UnitImage

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FactionUnitsScreen(
    navController: NavHostController,
    viewModel: FactionUnitsViewModel = hiltViewModel(),
    id: String
) {
    val lazyGridState = rememberLazyGridState()
    val faction: FactionUnitsState by viewModel.faction.observeAsState(
        initial = FactionUnitsState.Idle
    )
    viewModel.findUnitsByFaction(id)
    Scaffold(
        topBar = {
            val title = if (faction is FactionUnitsState.Success) {
                (faction as FactionUnitsState.Success).faction.subculture?.name.orEmpty()
            } else {
                ""
            }
            CustomToolbarWithBackArrow(title = "$title Units", navController = navController)
        },
        content = {
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource(R.drawable.backgroundttw),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
                when (val state = faction) {
                    is FactionUnitsState.Error -> {}
                    is FactionUnitsState.Idle,
                    is FactionUnitsState.Loading -> {
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
                                    units?.let {
                                        FactionUnitCard(
                                            state.faction.key.orEmpty(),
                                            units,
                                            state.gameVersion
                                        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactionUnitCard(
    faction_id: String,
    factionUnit: FactionUnitsQuery.Unit,
    gameVersion: String
) {
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    val isLarge = factionUnit.isLargeUnit()
    if (showCustomDialogWithResult) {
        UnitDialog(
            onDismiss = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            },
            onNegativeClick = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            },
            onPositiveClick = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            },
            id = factionUnit.unit.toString(),
            faction_id = faction_id
        )
    }
    Surface(
        modifier = Modifier
            .padding(5.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    showCustomDialogWithResult = true
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .paint(
                    painter = painterResource(R.drawable.unit_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // unit image
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
                    .weight(1f)
            ) {
                UnitImage(unit = factionUnit.map(), 110.dp, gameVersion)
            }
            // unit icon
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(1.dp)
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                UnitIconImage(unit = factionUnit.map(), size = 20.dp, gameVersion = gameVersion)
            }
            // unit data
            Column(
                modifier = Modifier.weight(4f)
            ) {
                Text(
                    text = factionUnit.land_unit?.onscreen_name.orEmpty(),
                    color = ColorOnPrimary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_treasury),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = factionUnit.multiplayer_cost.toString(),
                        color = ColorOnPrimary,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            if (isLarge) R.drawable.icon_entity_large else R.drawable.icon_entity_small
                        ),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = factionUnit.num_men.toString(),
                        color = ColorOnPrimary,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                }
                LazyRow(
                    modifier = Modifier.padding(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(factionUnit.land_unit?.abilities.orEmpty()) { item ->
                        UnitAbility(
                            id = item?.key.orEmpty(),
                            iconName = item?.icon_name.orEmpty(),
                            gameVersion = gameVersion,
                            size = 30.dp,
                            scope = rememberCoroutineScope()
                        )
                    }
                }
                LazyRow(
                    modifier = Modifier.padding(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(factionUnit.land_unit?.attributes.orEmpty()) { item ->
                        UnitAttribute(
                            id = item?.key.toString(),
                            tooltip = item?.bullet_text.orEmpty(),
                            gameVersion = gameVersion,
                            size = 30.dp,
                            scope = rememberCoroutineScope()
                        )
                    }
                }
                LazyRow(
                    modifier = Modifier.padding(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(factionUnit.land_unit?.special_ability_groups?.firstOrNull()?.abilities.orEmpty()) { item ->
                        UnitAbility(
                            id = item?.key.orEmpty(),
                            iconName = item?.icon_name.orEmpty(),
                            gameVersion = gameVersion,
                            size = 30.dp,
                            scope = rememberCoroutineScope()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UnitDialog(
    id: String,
    faction_id: String,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        UnitScreen(
            id = id,
            faction_id = faction_id
        )
    }
}

@Composable
fun Header(title: String) {
    Box(
        modifier = Modifier
            .zIndex(100f)
            .padding(top = 5.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.unit_background),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().height(30.dp),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillBounds
        )
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
    }
}
