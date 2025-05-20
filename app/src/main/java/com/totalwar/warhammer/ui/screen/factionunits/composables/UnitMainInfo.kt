package com.totalwar.warhammer.ui.screen.factionunits.composables


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.util.isLargeUnit

@Composable
fun UnitMainInfo(unit: FactionUnitsQuery.Unit) {
    val isLarge = unit.isLargeUnit()

    Text(
        text = unit.land_unit?.onscreen_name.orEmpty(),
        color = ColorOnPrimary,
        fontWeight = FontWeight.SemiBold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )

    InfoRow(R.drawable.icon_treasury, unit.multiplayer_cost.toString())
    InfoRow(
        if (isLarge) R.drawable.icon_entity_large else R.drawable.icon_entity_small,
        unit.num_men.toString()
    )
}
