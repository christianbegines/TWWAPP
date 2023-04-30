package com.totalwar.warhammer.util

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.UnitQuery

private const val UNIT_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/327635228256759215/ui/units/icons/%s.png"
private const val LORD_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/327635228256759215/"

private const val PORTHOLES = "portholes"
private const val UNITS = "units"

const val LORD_HERO = "Lord|Hero"

private fun checkIfLord(cast: String, url: String) {
}

fun formatUrlUnitImage(param: String): String = String.format(UNIT_URL, param)
fun formatUrlHeroLordImage(param: String): String =
    "$LORD_URL${param.replace(PORTHOLES, UNITS)}"

fun FactionUnitsQuery.Unit.map(): UnitQuery.Unit {
    return UnitQuery.Unit(
        null,
        null,
        null,
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
        null,
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
