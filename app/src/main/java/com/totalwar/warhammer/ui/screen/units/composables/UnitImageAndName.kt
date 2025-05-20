package com.totalwar.warhammer.ui.screen.units.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.ui.screen.components.UnitDetailImage
import com.totalwar.warhammer.viewmodels.units.UnitState

@Composable
fun UnitImageAndName(state: UnitState.Success) {
    UnitDetailImage(
        unit = state.unit, size = 130.dp, state.gameVersion
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "${state.unit.land_unit?.onscreen_name}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Image(
            painter = rememberAsyncImagePainter(
                "https://res.cloudinary.com/fishofstone/image/upload/w_64,f_auto/twwstats/api/${
                    state.gameVersion
                }/${state.faction.flags_url}/mon_64.jpg"
            ),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
        )
    }
}
