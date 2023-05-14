package com.totalwar.warhammer.util

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.UnitQuery

private const val UNIT_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/ui/units/icons/%s.png"
private const val LORD_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/"
private const val ICON_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/%s/ui/common ui/unit_category_icons/"
private const val ABILITY_ATTR_ICONS =
    "https://res.cloudinary.com/fishofstone/image/upload/w_64,h_64/twwstats/api/%s/ui/battle ui/ability_icons/"
private const val PORTHOLES = "portholes"
private const val UNITS = "units"
const val LORD = "Lord"
const val HERO = "Hero"
fun formatUrlUnitImage(gameVersion: String, param: String): String =
    String.format(UNIT_URL, gameVersion, param)

fun formatUrlHeroLordImage(param: String, gameVersion: String): String =
    "${String.format(LORD_URL, gameVersion)}${param.replace(PORTHOLES, UNITS)}"

fun formatUrlUnitIcon(param: String, gameVersion: String): String =
    "${String.format(ICON_URL, gameVersion)}$param.png"

fun formatUrlAbilityAttrIcon(param: String, gameVersion: String): String =
    "${String.format(ABILITY_ATTR_ICONS, gameVersion)}$param.png"

fun FactionUnitsQuery.Unit.map(): UnitQuery.Unit {
    return UnitQuery.Unit(
        null,
        null,
        this.unit_sets?.map {
            UnitQuery.Unit_set(
                special_category = it?.special_category.orEmpty(),
                __typename = it?.__typename.orEmpty()
            )
        },
        null,
        caste = this.caste,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        listOf(
            UnitQuery.Custom_battle_permission(
                general_portrait = this.custom_battle_permissions?.firstOrNull()?.general_portrait,
                campaign_exclusive = this.custom_battle_permissions?.map { it?.campaign_exclusive },
                set_piece_character = null,
                __typename = this.custom_battle_permissions?.firstOrNull()?.__typename.orEmpty()
            )
        ),
        ui_unit_group = UnitQuery.Ui_unit_group(
            key = this.ui_unit_group?.key,
            name = this.ui_unit_group?.name,
            tooltip = this.ui_unit_group?.tooltip,
            icon = this.ui_unit_group?.icon,
            __typename = this.ui_unit_group?.__typename.orEmpty()
        ),
        null,
        land_unit = UnitQuery.Land_unit(
            null, null, null,
            variant = UnitQuery.Variant(
                null,
                null,
                null,
                unit_card_url = this.land_unit?.variant?.unit_card_url,
                __typename = this.land_unit?.variant?.__typename.orEmpty()
            ),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            abilities = this.land_unit?.abilities?.map {
                UnitQuery.Ability1(
                    it?.icon_name,
                    it?.key,
                    it?.name,
                    it?.tooltip,
                    it?.__typename.orEmpty()
                )
            },
            attributes = this.land_unit?.attributes?.map {
                UnitQuery.Attribute(
                    key = it?.key,
                    bullet_text = it?.bullet_text,
                    imbued_effect_text = it?.imbued_effect_text,
                    __typename = it?.__typename.orEmpty()
                )
            },
            special_ability_groups = this.land_unit?.special_ability_groups?.map { special ->
                UnitQuery.Special_ability_group(
                    abilities = special?.abilities?.map { ability ->
                        UnitQuery.Ability2(
                            icon_name = ability?.icon_name,
                            key = ability?.key,
                            name = ability?.name,
                            tooltip = ability?.tooltip,
                            __typename = ability?.__typename.orEmpty()
                        )
                    },
                    __typename = special?.__typename.orEmpty()
                )
            },
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            __typename = this.land_unit?.__typename.orEmpty()
        ),
        this.__typename
    )
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
fun FactionUnitsQuery.Unit.isExclusive(): Boolean {
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

fun FactionUnitsQuery.Unit.isLargeUnit(): Boolean {
    return this.land_unit?.battle_entity?.size?.contains("large") == true
}

fun UnitQuery.Unit.isLargeUnit(): Boolean {
    return this.land_unit?.battle_entity?.battle_entity?.size?.contains("large") == true
}

fun FactionUnitsQuery.Unit.isLord(): Boolean {
    return this.caste.equals(LORD)
}

fun FactionUnitsQuery.Unit.isHero(): Boolean {
    return this.caste.equals(HERO)
}
