package com.totalwar.warhammer.util

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.UnitQuery

private const val UNIT_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/ui/units/icons/%s.png"
private const val LORD_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/"
private const val ICON_URL = "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/%s/ui/common ui/unit_category_icons/"
private const val PORTHOLES = "portholes"
private const val UNITS = "units"
const val LORD_HERO = "Lord|Hero"
fun formatUrlUnitImage(gameVersion: String, param: String): String =
    String.format(UNIT_URL, param, gameVersion)

fun formatUrlHeroLordImage(param: String, gameVersion: String): String =
    "${String.format(LORD_URL, gameVersion)}${param.replace(PORTHOLES, UNITS)}"

fun formatUrlUnitIcon(param: String, gameVersion: String): String =
    "${String.format(ICON_URL, gameVersion)}$param.png"

fun FactionUnitsQuery.Unit.map(): UnitQuery.Unit {
    return UnitQuery.Unit(
        null,
        null,
        this.unit_sets?.map{
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

            __typename = this.land_unit?.__typename.orEmpty()
        ),
        this.__typename
    )
}
