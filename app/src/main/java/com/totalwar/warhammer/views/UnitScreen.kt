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
import java.math.RoundingMode
import kotlin.math.roundToInt

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
                                .padding(
                                    top = if (selectedUnit.isRenown()) 10.dp else 40.dp,
                                    bottom = 20.dp
                                )
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
                                        statValue = selectedUnit.land_unit?.damage_mod_physical?.toString()
                                            ?: "0",
                                        statIcon = R.drawable.resistance_physical,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Missile Resistance",
                                        statValue = selectedUnit.land_unit?.damage_mod_missile?.toString()
                                            ?: "0",
                                        statIcon = R.drawable.resistance_missile,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Magic Resistance",
                                        statValue = selectedUnit.land_unit?.damage_mod_magic?.toString()
                                            ?: "0",
                                        statIcon = R.drawable.resistance_magic,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Fire Resistance",
                                        statValue = selectedUnit.land_unit?.damage_mod_flame?.toString()
                                            ?: "0",
                                        statIcon = R.drawable.resistance_fire,
                                        iconSize = 15.dp
                                    )
                                    UnitStat(
                                        statName = "Leadership",
                                        statValue = selectedUnit.land_unit?.morale.toString(),
                                        statIcon = R.drawable.icon_stat_morale
                                    )
                                    UnitStat(
                                        statName = "Speed",
                                        statValue = if (selectedUnit.land_unit?.mount != null) {
                                            selectedUnit.land_unit.mount.battle_entity?.battle_entity?.run_speed?.times(
                                                10
                                            )?.roundToInt().toString()
                                        } else {
                                            selectedUnit.land_unit?.battle_entity?.battle_entity?.run_speed?.times(
                                                10
                                            )?.roundToInt().toString()
                                        },
                                        statIcon = R.drawable.icon_stat_speed
                                    )
                                    UnitStat(
                                        statName = "Melee Attack",
                                        statValue = selectedUnit.land_unit?.melee_attack.toString(),
                                        statIcon = R.drawable.icon_stat_attack
                                    )
                                    UnitSubStat(
                                        statName = "Attack Interval",
                                        statValue = selectedUnit.land_unit?.primary_melee_weapon?.melee_attack_interval?.toBigDecimal()
                                            ?.setScale(1, RoundingMode.UP)?.toDouble().toString(),
                                        statIcon = R.drawable.icon_status_melee_24px,
                                        iconSize = 20.dp
                                    )
                                    UnitSubStat(
                                        statName = "Is High Threat",
                                        statValue = if (selectedUnit.is_high_threat == true) {
                                            "Yes"
                                        } else {
                                            "No"
                                        },
                                        statIcon = R.drawable.icon_status_alert_high_24px,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Splash Target Size",
                                        statValue =
                                        selectedUnit.land_unit?.primary_melee_weapon?.splash_attack_target_size.toString(),
                                        statIcon = R.drawable.fontawesome_street_view_icon,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Splash Max Attacks",
                                        statValue =
                                        selectedUnit.land_unit?.primary_melee_weapon?.splash_attack_max_attacks.toString(),
                                        statIcon = R.drawable.splash,
                                        iconSize = 15.dp
                                    )
                                    UnitStat(
                                        statName = "Melee Defense",
                                        statValue = selectedUnit.land_unit?.melee_defence.toString(),
                                        statIcon = R.drawable.icon_stat_defence
                                    )
                                    UnitStat(
                                        statName = "Weapon Strength",
                                        statValue = selectedUnit.land_unit?.primary_melee_weapon?.damage?.plus(
                                            selectedUnit.land_unit.primary_melee_weapon.ap_damage
                                                ?: 0
                                        ).toString(),
                                        statIcon = R.drawable.icon_stat_damage
                                    )
                                    UnitSubStat(
                                        statName = "Base Damage",
                                        statValue = selectedUnit.land_unit?.primary_melee_weapon?.damage.toString(),
                                        statIcon = R.drawable.icon_stat_damage,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "AP Damage",
                                        statValue = selectedUnit.land_unit?.primary_melee_weapon?.ap_damage.toString(),
                                        statIcon = R.drawable.modifier_icon_armour_piercing,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Bonus vs. Large",
                                        statValue = selectedUnit.land_unit?.primary_melee_weapon?.bonus_v_large?.toString()
                                            ?: "0",
                                        statIcon = R.drawable.modifier_icon_bonus_vs_large,
                                        iconSize = 15.dp
                                    )
                                    UnitSubStat(
                                        statName = "Bonus vs. Infantry",
                                        statValue = selectedUnit.land_unit?.primary_melee_weapon?.bonus_v_infantry?.toString()
                                            ?: "0",
                                        statIcon = R.drawable.modifier_icon_bonus_vs_infantry,
                                        iconSize = 15.dp
                                    )
                                    UnitStat(
                                        statName = "Charge Bonus",
                                        statValue = selectedUnit.land_unit?.charge_bonus.toString(),
                                        statIcon = R.drawable.icon_stat_charge_bonus
                                    )
                                    if (selectedUnit.land_unit?.primary_missile_weapon != null) {
                                        Text(
                                            text = "Primary Missile Weapon",
                                            modifier = Modifier.padding(5.dp),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        UnitStat(
                                            statName = "Ammunition",
                                            statValue = selectedUnit.land_unit?.secondary_ammo.toString(),
                                            statIcon = R.drawable.icon_stat_ammo
                                        )
                                        UnitStat(
                                            statName = "Range",
                                            statValue = selectedUnit.land_unit?.primary_missile_weapon?.default_projectile?.projectile?.effective_range.toString(),
                                            statIcon = R.drawable.icon_stat_range
                                        )
                                    }

                                    UnitStat(
                                        statName = "Leadership",
                                        statValue = selectedUnit.land_unit?.morale.toString(),
                                        statIcon = R.drawable.icon_stat_morale
                                    )
                                    UnitStat(
                                        statName = "Leadership",
                                        statValue = selectedUnit.land_unit?.morale.toString(),
                                        statIcon = R.drawable.icon_stat_morale
                                    )
                                    UnitStat(
                                        statName = "Leadership",
                                        statValue = selectedUnit.land_unit?.morale.toString(),
                                        statIcon = R.drawable.icon_stat_morale
                                    )
                                    UnitStat(
                                        statName = "Leadership",
                                        statValue = selectedUnit.land_unit?.morale.toString(),
                                        statIcon = R.drawable.icon_stat_morale
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
