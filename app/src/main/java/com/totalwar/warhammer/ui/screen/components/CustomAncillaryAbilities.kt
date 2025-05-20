package com.totalwar.warhammer.ui.screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.ui.screen.components.tootlips.UnitAbilityTooltip
import com.totalwar.warhammer.viewmodels.units.UnitState

@Composable
fun CustomAncillaryAbilities(unit: FactionUnitsQuery.Unit, gameVersion: String) {
    val abilities = unit.custom_battle_permissions
        ?.firstOrNull()
        ?.set_piece_character?.ancillaries
        .orEmpty()
        .flatMap { ancillary ->
            ancillary?.ancillary_effects?.firstOrNull {
                it?.effect?.abilities?.isNotEmpty() == true
            }?.effect?.abilities.orEmpty()
        }

    if (abilities.isNotEmpty()) {
        LazyRow(verticalAlignment = Alignment.CenterVertically) {
            items(abilities) { ability ->
                UnitAbilityTooltip(
                    id = ability?.effect_bonus?.value?.onAbility?.key.orEmpty(),
                    iconName = ability?.effect_bonus?.value?.onAbility?.icon_name.orEmpty(),
                    gameVersion = gameVersion,
                    size = 30.dp,
                    scope = rememberCoroutineScope()
                )
            }
        }
    }
}

@Composable
fun CustomAncillaryAbilities(state: UnitState.Success) {
    LazyRow(
        modifier = Modifier.padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val abilitiesCustom = state.unit.custom_battle_permissions
            ?.firstOrNull()
            ?.set_piece_character?.ancillaries.orEmpty()
        for (item in abilitiesCustom) {
            val abilities = item?.ancillary_effects?.firstOrNull {
                it?.effect?.abilities?.isNotEmpty() == true
            }?.effect?.abilities.orEmpty()
            items(abilities) { ability ->
                UnitAbilityTooltip(
                    id = ability?.effect_bonus?.value?.onAbility?.key.orEmpty(),
                    iconName = ability?.effect_bonus?.value?.onAbility?.icon_name.orEmpty(),
                    gameVersion = state.gameVersion,
                    size = 30.dp,
                    scope = rememberCoroutineScope()
                )
            }
        }

    }
}