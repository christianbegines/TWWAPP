package com.totalwar.warhammer.util

import com.totalwar.warhammer.UnitQuery

fun UnitQuery.Unit.isRenown(): Boolean {
    return this.unit_sets?.any { it?.special_category?.contains("renown") == true }
        ?.or(false) == true
}

fun UnitQuery.Unit.getUnitImageUrl(gameVersion: String): String {
    return if ("$LORD|$HERO".contains(this.caste.orEmpty())) {
        if (isExclusive()) {
            this.land_unit?.variant?.unit_card_url.let {
                formatUrlUnitImage(gameVersion, it.orEmpty())
            }
        } else {
            this.custom_battle_permissions?.first()?.general_portrait.let {
                formatUrlHeroLordImage(it.orEmpty(), gameVersion)
            }
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
