package com.totalwar.warhammer.ui.screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.totalwar.warhammer.R
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.fragment.Projectile
import com.totalwar.warhammer.util.calculateReloadTime
import com.totalwar.warhammer.util.missileDamage
import java.math.RoundingMode

private const val ZERO = "0"

@Composable
fun UnitMissileWeapon(
    gameVersion: String?,
    landUnit: UnitQuery.Land_unit,
    projectile: Projectile,
    title: String,
    ammo: Int?
) {
    Text(
        text = title,
        modifier = Modifier.padding(5.dp),
        fontWeight = FontWeight.SemiBold,
    )
    UnitStat(
        statName = "Ammunition",
        statValue = ammo?.toString() ?: ZERO,
        statIcon = R.drawable.icon_stat_ammo,
    )
    UnitStat(
        statName = "Range",
        statValue = projectile.effective_range?.toString() ?: ZERO,
        statIcon = R.drawable.icon_stat_range,
    )
    UnitStat(
        gameVersion = gameVersion,
        statName = "Missile Damage",
        statValue =
        missileDamage(
            projectile,
            landUnit.reload?.toDouble(),
        ).toString(),
        statIcon = R.drawable.icon_stat_ranged_damage,
        magical = projectile.is_magical == true,
        ignition = projectile.ignition_amount ?: 0 > 0,
        contact = projectile.contact_stat_effect?.contact_phase
    )
    UnitSubStat(
        statName = "Missile Base Damage",
        statValue = projectile.damage?.toString() ?: ZERO,
        statIcon = R.drawable.icon_stat_ranged_damage,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Missile AP Damage",
        statValue = projectile.ap_damage?.toString() ?: ZERO,
        statIcon = R.drawable.modifier_icon_armour_piercing_ranged,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Bonus vs. Infantry",
        statValue = projectile.bonus_v_infantry?.toString() ?: ZERO,
        statIcon = R.drawable.modifier_icon_bonus_vs_infantry,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Bonus vs. Large",
        statValue = projectile.bonus_v_large?.toString() ?: ZERO,
        statIcon = R.drawable.modifier_icon_bonus_vs_large,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Explosion Base Dmg.",
        statValue = projectile.explosion?.explosion?.detonation_damage?.toString() ?: ZERO,
        statIcon = R.drawable.icon_stat_ranged_damage,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Explosion AP Dmg.",
        statValue = projectile.explosion?.explosion?.detonation_damage_ap?.toString() ?: ZERO,
        statIcon = R.drawable.modifier_icon_armour_piercing_ranged,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Detonation Radius",
        statValue = projectile.explosion?.explosion?.detonation_radius?.toString() ?: ZERO,
        statIcon = R.drawable.fontawesome_street_view_icon,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Shots Per Volley",
        statValue = projectile.shots_per_volley?.toString()
            ?: ZERO,
        statIcon = R.drawable.icon_status_firing_24px,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Projectile Number",
        statValue = projectile.projectile_number?.toString() ?: ZERO,
        statIcon = R.drawable.icon_status_firing_24px,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Reload Time",
        statValue = calculateReloadTime(projectile, landUnit.reload?.toDouble()).toString(),
        statIcon = R.drawable.icon_cooldown,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Total Accuracy",
        statValue = projectile.marksmanship_bonus?.plus(
            landUnit.accuracy ?: 0
        )?.toInt()?.toString() ?: ZERO,
        statIcon = R.drawable.spacebar_fire_arc,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Calibration Distance",
        statValue = projectile.calibration_distance?.toInt()?.toString()
            ?: ZERO,
        statIcon = R.drawable.icon_distance_to_target,
        iconSize = 15.dp,
    )
    UnitSubStat(
        statName = "Calibration Area",
        statValue = projectile.calibration_area?.toBigDecimal()
            ?.setScale(1, RoundingMode.UP)?.toDouble()?.toString() ?: ZERO,
        statIcon = R.drawable.help_page_drag,
        iconSize = 15.dp,
    )

}