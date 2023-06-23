package com.totalwar.warhammer.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.theme.BulletBackground
import com.totalwar.warhammer.util.isRenown
import com.totalwar.warhammer.viewmodels.units.UnitState
import com.totalwar.warhammer.viewmodels.units.UnitViewModel
import com.totalwar.warhammer.views.common.UnitBullets
import com.totalwar.warhammer.views.common.UnitDetailImage
import com.totalwar.warhammer.views.common.UnitIconImage
import com.totalwar.warhammer.views.common.UnitStat
import com.totalwar.warhammer.views.common.UnitSubStat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnitScreen(
    viewModel: UnitViewModel = hiltViewModel(),
    id: String,
    faction_id: String
) {
    val unit: UnitState by viewModel.unit.observeAsState(initial = UnitState.Idle)
    viewModel.findUnitById(id, faction_id)
    Scaffold(
        modifier = Modifier.padding(5.dp),
        backgroundColor = Color.Transparent
    ) {
        when (val state = unit) {
            is UnitState.Error -> {}
            is UnitState.Idle,
            is UnitState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UnitState.Success -> {
                val selectedUnit = state.unit
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .zIndex(100f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.roll_top),
                            contentDescription = "",
                            modifier = Modifier.fillMaxWidth(),
                            alignment = Alignment.TopCenter,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.unit_background),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                        Column(
                            modifier = Modifier
                                .padding(top = if (selectedUnit.isRenown()) 10.dp else 40.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top

                        ) {
                            UnitDetailImage(
                                unit = selectedUnit,
                                size = 135.dp,
                                state.gameVersion
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${selectedUnit.land_unit?.onscreen_name}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Image(
                                    painter = rememberAsyncImagePainter("https://res.cloudinary.com/fishofstone/image/upload/w_64,f_auto/twwstats/api/327635228256759215/${state.faction.flags_url}/mon_64.jpg"),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.padding(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                UnitIconImage(
                                    unit = selectedUnit,
                                    size = 20.dp,
                                    gameVersion = state.gameVersion
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "${selectedUnit.ui_unit_group?.name}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                                Column(
                                    modifier = Modifier
                                        .border(1.dp, BulletBackground)
                                        .background(
                                            Color.Transparent.copy(0.1f)
                                        ),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    UnitBullets(selectedUnit)
                                }
                            }
                            Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
                                Column(
                                    modifier = Modifier
                                        .border(1.dp, BulletBackground)
                                        .background(
                                            Color.Transparent.copy(0.1f)
                                        ),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    UnitStat(
                                        statName = "MP Cost",
                                        statValue = selectedUnit.multiplayer_cost.toString(),
                                        statIcon = R.drawable.icon_treasury
                                    )
                                    UnitStat(
                                        statName = "Health",
                                        statValue =
                                        selectedUnit.land_unit?.bonus_hit_points?.plus(
                                            selectedUnit.land_unit.battle_entity?.battle_entity?.hit_points
                                                ?: 0
                                        )?.times((selectedUnit.num_men ?: 1)).toString(),
                                        statIcon = R.drawable.icon_stat_health
                                    )
                                    UnitSubStat(
                                        statName = "Health per entity",
                                        statValue = selectedUnit.land_unit?.bonus_hit_points?.plus(
                                            selectedUnit.land_unit.battle_entity?.battle_entity?.hit_points
                                                ?: 0
                                        ).toString(),
                                        statIcon = R.drawable.spacebar_unit_health_ammo,
                                        iconSize = 30.dp
                                    )
                                    UnitStat(
                                        statName = "Barrier",
                                        statValue = selectedUnit.barrier_health?.toInt().toString(),
                                        statIcon = R.drawable.barrier
                                    )
                                    UnitStat(
                                        statName = "Armor",
                                        statValue = selectedUnit.land_unit?.armour?.armour_value.toString(),
                                        statIcon = R.drawable.icon_stat_armour
                                    )
                                    UnitSubStat(
                                        statName = "Parry Chance",
                                        statValue = selectedUnit.land_unit?.shield?.parry_chance.toString(),
                                        statIcon = when (selectedUnit.land_unit?.shield?.material) {
                                            "wood" -> {
                                                R.drawable.modifier_icon_shield1
                                            }
                                            "metal" -> {
                                                R.drawable.modifier_icon_shield2
                                            }
                                            else -> {
                                                null
                                            }
                                        },
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Physical Resistance",
                                        statValue = selectedUnit.land_unit?.damage_mod_physical?.toString() ?: "0",
                                        statIcon = R.drawable.resistance_physical,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Missile Resistance",
                                        statValue = selectedUnit.land_unit?.damage_mod_missile?.toString() ?: "0",
                                        statIcon = R.drawable.resistance_missile,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Magic Resistance",
                                        statValue = selectedUnit.land_unit?.damage_mod_magic?.toString() ?: "0",
                                        statIcon = R.drawable.resistance_magic,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Fire Resistance",
                                        statValue = selectedUnit.land_unit?.damage_mod_flame?.toString() ?: "0",
                                        statIcon = R.drawable.resistance_fire,
                                        iconSize = 15.dp
                                    )
                                }
                            }
                        }
                    }
                    Box(contentAlignment = Alignment.BottomCenter) {
                        Image(
                            painter = painterResource(id = R.drawable.roll_bottom),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .zIndex(100f),
                            alignment = Alignment.BottomCenter,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
