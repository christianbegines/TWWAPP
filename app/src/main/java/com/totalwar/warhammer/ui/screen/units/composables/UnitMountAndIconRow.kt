package com.totalwar.warhammer.ui.screen.units.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.totalwar.warhammer.ui.screen.components.UnitIconImage
import com.totalwar.warhammer.ui.screen.components.tootlips.UnitMountTooltip
import com.totalwar.warhammer.viewmodels.units.UnitState
import com.totalwar.warhammer.viewmodels.units.UnitViewModel

@Composable
fun UnitMountAndIconRow(
    factionId: String, state: UnitState.Success, viewModel: UnitViewModel
) {
    Row(
        modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        if (state.unit.battle_mounts?.isNotEmpty() == true) {
            UnitMountTooltip(
                factionId,
                state.unit.land_unit?.key.toString(),
                scope = rememberCoroutineScope(),
                state.unit.land_unit?.mount,
                state.gameVersion,
                state.unit.battle_mounts,
                viewModel
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        UnitIconImage(unit = state.unit, size = 20.dp, gameVersion = state.gameVersion)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "${state.unit.ui_unit_group?.name}",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
