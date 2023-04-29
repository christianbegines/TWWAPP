package com.totalwar.warhammer.views

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.navigation.AppScreens
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.util.CustomToolbar
import com.totalwar.warhammer.viewmodels.AppViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FactionUnitsScreen(
    navController: NavController,
    viewModel: AppViewModel,
    dataStore: DataStore<Settings>,
    openDrawer: () -> Unit,
    id: String
) {
    val settings: Settings? by dataStore.data.collectAsState(
        initial = null
    )
    val unitList: List<FactionUnitsQuery.Unit?> by viewModel.unitsFactionList.observeAsState(
        initial = listOf()
    )
    settings?.let { viewModel.findUnitsByFaction(id, it.gameVersion) }
    val lazyGridState = rememberLazyGridState()
    Scaffold(
        topBar = {
            CustomToolbar(title = stringResource(id = R.string.app_name), openDrawer)
        },
        content = {
            if (unitList.isNotEmpty()) {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(vertical = 4.dp),
                        state = lazyGridState
                    ) {
                        items(unitList) { units ->
                            units?.let {
                                FactionUnitCard(factionUnit = units, navController = navController)
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
fun FactionUnitCard(factionUnit: FactionUnitsQuery.Unit, navController: NavController) {
    val expanded by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
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
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                val url = if (factionUnit.caste != "Lord" && factionUnit.caste != "Hero") {
                    factionUnit.land_unit?.variant?.unit_card_url.let {
                        "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/327635228256759215/ui/units/icons/$it.png"
                    }
                } else {
                    factionUnit.custom_battle_permissions?.first()?.general_portrait?.let {
                        "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/327635228256759215/${it.replace("portholes","units")}"
                    }
                }
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
            }
            Row {
                factionUnit.land_unit?.onscreen_name?.let {
                    Text(
                        text = it,
                        color = ColorOnPrimary
                    )
                }
            }
        }
    }
}
