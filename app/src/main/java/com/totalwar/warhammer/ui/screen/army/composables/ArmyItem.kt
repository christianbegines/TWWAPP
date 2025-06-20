package com.totalwar.warhammer.ui.screen.army.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.viewmodels.armies.ArmyUi

@Composable
fun ArmyItem(army: ArmyUi, gameVersion: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(5.dp)
            .paint(
                painter = painterResource(R.drawable.unit_background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        if (army.flagUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(
                    "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/${gameVersion}/${army.flagUrl}/mon_64.webp"
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .size(130.dp)
            )
        }

        Text(
            text = army.name,
            fontSize = 15.sp,
            color = ColorOnPrimary,
            modifier = Modifier.padding(10.dp)
        )
    }
}