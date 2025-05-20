package com.totalwar.warhammer.ui.screen.units.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.ui.screen.components.UnitBullets
import com.totalwar.warhammer.ui.theme.BulletBackground

@Composable
fun UnitBulletsSection(selectedUnit: UnitQuery.Unit) {
    Row(modifier = Modifier.padding(horizontal = 20.dp)) {
        Column(
            modifier = Modifier
                .border(1.dp, BulletBackground)
                .background(
                    Color.Transparent.copy(0.1f),
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnitBullets(selectedUnit)
        }
    }
}