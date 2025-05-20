package com.totalwar.warhammer.ui.screen.factionunits.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.R
import com.totalwar.warhammer.util.map
import com.totalwar.warhammer.ui.screen.components.UnitIconImage
import com.totalwar.warhammer.ui.screen.components.UnitImage
import com.totalwar.warhammer.ui.screen.components.dialog.UnitDialog
import com.totalwar.warhammer.ui.screen.components.CustomAncillaryAbilities
import com.totalwar.warhammer.ui.screen.components.UnitAbilities1Row
import com.totalwar.warhammer.ui.screen.components.UnitAbilities2Row
import com.totalwar.warhammer.ui.screen.components.UnitAttributesRow

@Composable
fun UnitListCard(
    factionId: String,
    factionUnit: FactionUnitsQuery.Unit?,
    gameVersion: String
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        UnitDialog(
            onDismiss = {
                showDialog = false
            },
            id = factionUnit?.unit.toString(),
            factionId = factionId
        )
    }
    Surface(
        modifier = Modifier
            .padding(5.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    showDialog = true
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .paint(
                    painter = painterResource(R.drawable.unit_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            factionUnit?.let {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f, true)
                ) {
                    UnitImage(unit = it.map(), 110.dp, gameVersion)
                }
                UnitIconImage(unit = it.map(), size = 20.dp, gameVersion = gameVersion)
                Column(
                    modifier = Modifier.weight(4f)
                ) {
                    UnitMainInfo(it)
                    CustomAncillaryAbilities(it, gameVersion)
                    UnitAttributesRow(it.land_unit?.attributes.orEmpty(), gameVersion)
                    UnitAbilities1Row(it.land_unit?.abilities.orEmpty(), gameVersion)
                    for (abilities in it.land_unit?.special_ability_groups.orEmpty()) {
                        UnitAbilities2Row(
                            abilities?.abilities.orEmpty(),
                            gameVersion
                        )
                    }

                }
            }

        }
    }
}