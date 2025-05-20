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
fun UnitAbilities1Row(abilities: List<FactionUnitsQuery.Ability1?>?, gameVersion: String) {
    if (!abilities.isNullOrEmpty()) {
        LazyRow(verticalAlignment = Alignment.CenterVertically) {
            items(abilities) { ability ->
                UnitAbilityTooltip(
                    id = ability?.key.orEmpty(),
                    iconName = ability?.icon_name.orEmpty(),
                    gameVersion = gameVersion,
                    size = 30.dp,
                    scope = rememberCoroutineScope()
                )
            }
        }
    }
}

@Composable
fun UnitAbilities1Row(state: UnitState.Success) {
    if (state.unit.land_unit?.abilities.isNullOrEmpty()) {
        LazyRow(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(state.unit.land_unit?.abilities.orEmpty()) { item ->
                UnitAbilityTooltip(
                    id = item?.key.orEmpty(),
                    iconName = item?.icon_name.orEmpty(),
                    gameVersion = state.gameVersion,
                    size = 30.dp,
                    scope = rememberCoroutineScope()
                )
            }
        }
    }
}