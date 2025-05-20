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
import com.totalwar.warhammer.ui.screen.components.tootlips.UnitAttributeTooltip
import com.totalwar.warhammer.viewmodels.units.UnitState

@Composable
fun UnitAttributesRow(attributes: List<FactionUnitsQuery.Attribute?>, gameVersion: String) {
    if (attributes.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(attributes) { attr ->
                UnitAttributeTooltip(
                    id = attr?.key.orEmpty(),
                    tooltip = attr?.bullet_text.orEmpty(),
                    gameVersion = gameVersion,
                    size = 30.dp,
                    scope = rememberCoroutineScope()
                )
            }
        }
    }
}

@Composable
fun UnitAttributesRow(state: UnitState.Success) {
    LazyRow(
        modifier = Modifier.padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(state.unit.land_unit?.attributes.orEmpty()) { item ->
            UnitAttributeTooltip(
                id = item?.key.toString(),
                tooltip = item?.bullet_text.orEmpty(),
                gameVersion = state.gameVersion,
                size = 30.dp,
                scope = rememberCoroutineScope()
            )
        }
    }
}