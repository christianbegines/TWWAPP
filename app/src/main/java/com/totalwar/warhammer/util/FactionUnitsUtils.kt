package com.totalwar.warhammer.util

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.UnitQuery

const val LORD = "Lord"
const val HERO = "Hero"
private const val CAMPAIGN_EXCLUSIVE = "Campaign Exclusive"
fun FactionUnitsQuery.Unit.map(): UnitQuery.Unit {
    return UnitQuery.Unit(
        null,
        null,
        this.unit_sets?.map {
            UnitQuery.Unit_set(
                special_category = it?.special_category.orEmpty()
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
                set_piece_character = null
            )
        ),
        ui_unit_group = UnitQuery.Ui_unit_group(
            key = this.ui_unit_group?.key,
            name = this.ui_unit_group?.name,
            tooltip = this.ui_unit_group?.tooltip,
            parent_group = UnitQuery.Parent_group(
                key = this.ui_unit_group?.parent_group?.key,
                onscreen_name = this.ui_unit_group?.parent_group?.onscreen_name,
                order = this.ui_unit_group?.parent_group?.order
            ),
            icon = this.ui_unit_group?.icon
        ),
        null,
        land_unit = UnitQuery.Land_unit(
            null, null, null,
            variant = UnitQuery.Variant(
                null,
                null,
                null,
                unit_card_url = this.land_unit?.variant?.unit_card_url
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
                    it?.tooltip
                )
            },
            attributes = this.land_unit?.attributes?.map {
                UnitQuery.Attribute(
                    key = it?.key,
                    bullet_text = it?.bullet_text,
                    imbued_effect_text = it?.imbued_effect_text
                )
            },
            special_ability_groups = this.land_unit?.special_ability_groups?.map { special ->
                UnitQuery.Special_ability_group(
                    abilities = special?.abilities?.map { ability ->
                        UnitQuery.Ability2(
                            icon_name = ability?.icon_name,
                            key = ability?.key,
                            name = ability?.name,
                            tooltip = ability?.tooltip
                        )
                    }
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
            null
        )
    )
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

fun FactionUnitsQuery.Unit.isLargeUnit(): Boolean {
    return this.land_unit?.battle_entity?.size?.contains("large") == true
}

fun FactionUnitsQuery.Unit.isLord(): Boolean {
    return this.caste.equals(LORD)
}

fun FactionUnitsQuery.Unit.isHero(): Boolean {
    return this.caste.equals(HERO)
}

fun FactionUnitsQuery.Faction.getUnitsByType(): Map<String, List<FactionUnitsQuery.Unit?>> {
    val lords = this.units?.filter { unit -> unit?.isLord() == true }.orEmpty()
    val heroes = this.units?.filter { unit -> unit?.isHero() == true }.orEmpty()
    val units =
        this.units?.filter { unit -> unit?.isExclusive() == false && !unit.isHero() && !unit.isLord() }
            ?.groupBy { unit ->
                unit?.ui_unit_group?.parent_group?.onscreen_name.orEmpty()
            }?.toList()
            ?.sortedBy { it -> it.second.first()?.ui_unit_group?.parent_group?.order }
            ?.toMap()?.toMutableMap().also { list ->
                this.units?.filter { unit -> unit?.isExclusive() == true }
                    .takeIf { it?.isNotEmpty() == true }?.let {
                        list?.put(
                            CAMPAIGN_EXCLUSIVE,
                            it
                        )
                    }
            }.orEmpty().toMutableMap()
    val result = mutableMapOf(
        LORD to lords,
        HERO to heroes
    )
    result.putAll(units)
    return result.toMutableMap()
}
