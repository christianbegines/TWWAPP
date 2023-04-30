package com.totalwar.warhammer.views

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.datastore.core.DataStore
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.navigation.AppScreens
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.util.CustomToolbarWithBackArrow
import com.totalwar.warhammer.util.map
import com.totalwar.warhammer.viewmodels.AppViewModel
import com.totalwar.warhammer.views.common.UnitIconImage
import com.totalwar.warhammer.views.common.UnitImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FactionUnitsScreen(
    navController: NavHostController,
    viewModel: AppViewModel,
    dataStore: DataStore<Settings>,
    id: String
) {
    val lazyGridState = rememberLazyGridState()
    val settings: Settings? by dataStore.data.collectAsState(
        initial = null
    )
    val unitList: List<FactionUnitsQuery.Unit?> by viewModel.unitsFactionList.observeAsState(
        initial = listOf()
    )
    val gameVersion: String = settings?.let { it.gameVersion }.toString()
    viewModel.findUnitsByFaction(id, gameVersion)

    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(title = "List of units", navController = navController)
        },
        content = {
            if (unitList.isNotEmpty()) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painter = painterResource(R.drawable.backgroundttw),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.padding(vertical = 4.dp),
                        state = lazyGridState
                    ) {
                        items(unitList) { units ->
                            units?.let {
                                FactionUnitCard(
                                    factionUnit = units,
                                    navController = navController,
                                    gameVersion
                                )
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No Factions to SHOW",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )
}

@Composable
fun FactionUnitCard(
    factionUnit: FactionUnitsQuery.Unit,
    navController: NavController,
    gameVersion: String
) {
    val isLarge = factionUnit.land_unit?.battle_entity?.size?.contains("large")
    Surface(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .height(IntrinsicSize.Min),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate(
                        AppScreens.UnitScreen.routeWithArgs(
                            factionUnit.unit.toString()
                        )
                    )
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
                .padding(10.dp)
        ) {
            Column {
                UnitImage(unit = factionUnit.map(), 90.dp, gameVersion)
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                UnitIconImage(unit = factionUnit.map(), size = 20.dp, gameVersion = gameVersion)
            }
            Column {
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
                        painter = painterResource(id = R.drawable.icon_income),
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
                            if(isLarge == true) R.drawable.icon_entity_large else R.drawable.icon_entity_small
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
            }
        }
    }
}
