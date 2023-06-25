package com.totalwar.warhammer.util

import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.fragment.Projectile
import java.math.RoundingMode
import kotlin.math.round
import kotlin.math.roundToInt

fun UnitQuery.Unit.isRenown(): Boolean {
    return this.unit_sets?.any { it?.special_category?.contains("renown") == true }
        ?.or(false) == true
}

fun UnitQuery.Unit.getUnitImageUrl(gameVersion: String): String {
    return if ("$LORD|$HERO".contains(this.caste.orEmpty())) {
        this.custom_battle_permissions?.first()?.general_portrait.let {
            formatUrlHeroLordImage(it.orEmpty(), gameVersion)
        }
    } else {
        this.land_unit?.variant?.unit_card_url.let {
            formatUrlUnitImage(gameVersion, it.orEmpty())
        }
    }
}

fun UnitQuery.Unit.isExclusive(): Boolean {
    return this.custom_battle_permissions?.all { it ->
        when (val exclusive = it?.campaign_exclusive) {
            is List<*> -> {
                exclusive.all { it as Boolean }
            }

            is Boolean -> {
                exclusive == true
            }

            else -> {
                false
            }
        }
    } == true
}

fun exactReloadTime(projectile: Projectile, reload: Double): Double? {
    return projectile.base_reload_time?.times(1.0 - reload / 100)
}

fun calculateReloadTime(projectile: Projectile, reload: Double?): Double? {
    val reloadTime = (
        reload ?: projectile.base_reload_time
        ).let {
        exactReloadTime(
            projectile = projectile,
            it ?: 10.0,
        )
    }
    return reloadTime?.toBigDecimal()
        ?.setScale(2, RoundingMode.DOWN)?.toDouble()
}

fun missileDamage(projectile: Projectile, reload: Double?): Int {
    var dmg = projectile.damage?.plus(projectile.ap_damage ?: 0) ?: 1
    projectile.explosion?.explosion?.let { explosion ->
        dmg += explosion.detonation_damage ?: 0
        dmg += explosion.detonation_damage_ap ?: 0
    }
    val reloadTime = (
        reload ?: projectile.base_reload_time
        ).let {
        exactReloadTime(
            projectile = projectile,
            it ?: 10.0,
        )
    }
    val dps = (
        dmg.times(projectile.shots_per_volley ?: 1)
            .times(projectile.projectile_number ?: 1)
            .times(projectile.burst_size ?: 1)
            .times(10)
        ).div(reloadTime ?: 10.0)
    return dps.roundToInt()
}
