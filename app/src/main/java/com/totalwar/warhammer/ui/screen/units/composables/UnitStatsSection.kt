package com.totalwar.warhammer.ui.screen.units.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.screen.components.UnitMissileWeapon
import com.totalwar.warhammer.ui.screen.components.UnitStat
import com.totalwar.warhammer.ui.screen.components.UnitSubStat
import com.totalwar.warhammer.ui.theme.BulletBackground
import com.totalwar.warhammer.viewmodels.units.UnitState
import java.math.RoundingMode
import kotlin.math.roundToInt

@Composable
fun UnitStatsSection(state: UnitState.Success) {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Column(
            modifier = Modifier
                .border(1.dp, BulletBackground)
                .background(
                    Color.Transparent.copy(0.1f),
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnitStat(
                statName = "MP Cost",
                statValue = state.unit.multiplayer_cost.toString(),
                statIcon = R.drawable.icon_treasury,
            )
            UnitStat(
                statName = "Health",
                statValue = state.unit.land_unit?.bonus_hit_points?.plus(
                    state.unit.land_unit.battle_entity?.battle_entity?.hit_points
                        ?: 0,
                )?.plus(
                    state.unit.land_unit.mount?.battle_entity?.battle_entity?.hit_points
                        ?: 0,
                )?.times((state.unit.num_men ?: 1)).toString(),
                statIcon = R.drawable.icon_stat_health,
            )
            UnitSubStat(
                statName = "Health per entity",
                statValue = state.unit.land_unit?.bonus_hit_points?.plus(
                    state.unit.land_unit.battle_entity?.battle_entity?.hit_points
                        ?: 0,
                )?.plus(
                    state.unit.land_unit.mount?.battle_entity?.battle_entity?.hit_points
                        ?: 0,
                ).toString(),
                statIcon = R.drawable.spacebar_unit_health_ammo,
                iconSize = 30.dp,
            )
            UnitStat(
                statName = "Barrier",
                statValue = state.unit.barrier_health?.toInt().toString(),
                statIcon = R.drawable.barrier,
            )
            UnitStat(
                statName = "Armor",
                statValue = state.unit.land_unit?.armour?.armour_value.toString(),
                statIcon = R.drawable.icon_stat_armour,
            )
            UnitSubStat(
                statName = "Parry Chance",
                statValue = state.unit.land_unit?.shield?.parry_chance.toString(),
                statIcon =
                    if ((state.unit.land_unit?.shield?.parry_chance
                            ?: 0) <= 35
                    ) {
                        R.drawable.modifier_icon_shield1
                    } else if ((state.unit.land_unit?.shield?.parry_chance
                            ?: 0) >= 35
                    ) {
                        R.drawable.modifier_icon_shield2
                    } else {
                        null
                    },
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Physical Resistance",
                statValue = state.unit.land_unit?.damage_mod_physical?.toString()
                    ?: "0",
                statIcon = R.drawable.resistance_physical,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Missile Resistance",
                statValue = state.unit.land_unit?.damage_mod_missile?.toString()
                    ?: "0",
                statIcon = R.drawable.resistance_missile,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Magic Resistance",
                statValue = state.unit.land_unit?.damage_mod_magic?.toString()
                    ?: "0",
                statIcon = R.drawable.resistance_magic,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Fire Resistance",
                statValue = state.unit.land_unit?.damage_mod_flame?.toString()
                    ?: "0",
                statIcon = R.drawable.resistance_fire,
                iconSize = 15.dp,
            )
            UnitStat(
                statName = "Leadership",
                statValue = state.unit.land_unit?.morale.toString(),
                statIcon = R.drawable.icon_stat_morale,
            )
            UnitStat(
                statName = "Speed",
                statValue = if (state.unit.land_unit?.mount != null) {
                    state.unit.land_unit.mount.battle_entity?.battle_entity?.run_speed?.times(
                        10,
                    )?.roundToInt().toString()
                } else {
                    state.unit.land_unit?.battle_entity?.battle_entity?.run_speed?.times(
                        10,
                    )?.roundToInt().toString()
                },
                statIcon = R.drawable.icon_stat_speed,
            )
            UnitStat(
                gameVersion = state.gameVersion,
                statName = "Melee Attack",
                statValue = state.unit.land_unit?.melee_attack.toString(),
                statIcon = R.drawable.icon_stat_attack,
                magical = state.unit.land_unit?.primary_melee_weapon?.is_magical == true,
                ignition = state.unit.land_unit?.primary_melee_weapon?.ignition_amount ?: 0 > 0,
                contact = state.unit.land_unit?.primary_melee_weapon?.contact_phase?.contact_phase
            )
            UnitSubStat(
                statName = "Attack Interval",
                statValue = state.unit.land_unit?.primary_melee_weapon?.melee_attack_interval?.toBigDecimal()
                    ?.setScale(1, RoundingMode.UP)?.toDouble().toString(),
                statIcon = R.drawable.icon_status_melee_24px,
                iconSize = 20.dp,
            )
            UnitSubStat(
                statName = "Is High Threat",
                statValue = if (state.unit.is_high_threat == true) {
                    "Yes"
                } else {
                    "No"
                },
                statIcon = R.drawable.icon_status_alert_high_24px,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Splash Target Size",
                statValue = state.unit.land_unit?.primary_melee_weapon?.splash_attack_target_size.toString(),
                statIcon = R.drawable.fontawesome_street_view_icon,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Splash Max Attacks",
                statValue = state.unit.land_unit?.primary_melee_weapon?.splash_attack_max_attacks.toString(),
                statIcon = R.drawable.splash,
                iconSize = 15.dp,
            )
            UnitStat(
                statName = "Melee Defense",
                statValue = state.unit.land_unit?.melee_defence.toString(),
                statIcon = R.drawable.icon_stat_defence,
            )
            UnitStat(
                statName = "Weapon Strength",
                statValue = state.unit.land_unit?.primary_melee_weapon?.damage?.plus(
                    state.unit.land_unit.primary_melee_weapon.ap_damage
                        ?: 0,
                ).toString(),
                statIcon = R.drawable.icon_stat_damage,
            )
            UnitSubStat(
                statName = "Base Damage",
                statValue = state.unit.land_unit?.primary_melee_weapon?.damage.toString(),
                statIcon = R.drawable.icon_stat_damage,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "AP Damage",
                statValue = state.unit.land_unit?.primary_melee_weapon?.ap_damage.toString(),
                statIcon = R.drawable.modifier_icon_armour_piercing,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Bonus vs. Large",
                statValue = state.unit.land_unit?.primary_melee_weapon?.bonus_v_large?.toString()
                    ?: "0",
                statIcon = R.drawable.modifier_icon_bonus_vs_large,
                iconSize = 15.dp,
            )
            UnitSubStat(
                statName = "Bonus vs. Infantry",
                statValue = state.unit.land_unit?.primary_melee_weapon?.bonus_v_infantry?.toString()
                    ?: "0",
                statIcon = R.drawable.modifier_icon_bonus_vs_infantry,
                iconSize = 15.dp,
            )
            UnitStat(
                statName = "Charge Bonus",
                statValue = state.unit.land_unit?.charge_bonus.toString(),
                statIcon = R.drawable.icon_stat_charge_bonus,
            )
            if (state.unit.land_unit?.primary_missile_weapon?.default_projectile?.projectile != null) {
                UnitMissileWeapon(
                    gameVersion = state.gameVersion,
                    landUnit = state.unit.land_unit,
                    ammo = state.unit.land_unit.primary_ammo,
                    projectile = state.unit.land_unit.primary_missile_weapon.default_projectile.projectile,
                    title = "Primary Missile Weapon"
                )
            } else if (state.unit.land_unit?.engine?.missile_weapon?.default_projectile?.projectile != null) {
                UnitMissileWeapon(
                    gameVersion = state.gameVersion,
                    landUnit = state.unit.land_unit,
                    ammo = state.unit.land_unit.primary_ammo,
                    projectile = state.unit.land_unit.engine.missile_weapon.default_projectile.projectile,
                    title = "Primary Missile Weapon"
                )
            }
            if (state.unit.land_unit?.primary_missile_weapon?.default_projectile?.projectile != null && state.unit.land_unit.primary_missile_weapon.use_secondary_ammo_pool == true) {
                UnitMissileWeapon(
                    gameVersion = state.gameVersion,
                    title = "Secondary Missile Weapon",
                    ammo = state.unit.land_unit.secondary_ammo,
                    landUnit = state.unit.land_unit,
                    projectile = state.unit.land_unit.primary_missile_weapon.default_projectile.projectile
                )
            } else if (state.unit.land_unit?.engine?.missile_weapon?.default_projectile?.projectile != null && state.unit.land_unit.engine.missile_weapon.use_secondary_ammo_pool == true) {
                UnitMissileWeapon(
                    gameVersion = state.gameVersion,
                    landUnit = state.unit.land_unit,
                    ammo = state.unit.land_unit.secondary_ammo,
                    projectile = state.unit.land_unit.engine.missile_weapon.default_projectile.projectile,
                    title = "Secondary Missile Weapon"
                )
            }
            UnitStat(
                statName = "Mass",
                statValue = if (state.unit.land_unit?.mount != null) {
                    state.unit.land_unit.mount.battle_entity?.battle_entity?.mass?.toInt()
                        .toString()
                } else {
                    state.unit.land_unit?.battle_entity?.battle_entity?.mass?.toInt()
                        .toString()
                },
                statIcon = R.drawable.resource_marble,
            )
        }
    }

}