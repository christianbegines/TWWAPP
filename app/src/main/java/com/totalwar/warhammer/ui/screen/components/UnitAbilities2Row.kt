package com.totalwar.warhammer.ui.screen.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.ui.screen.components.tootlips.UnitAbilityTooltip

@Composable
fun UnitAbilities2Row(abilities: List<FactionUnitsQuery.Ability2?>?, gameVersion: String) {
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
