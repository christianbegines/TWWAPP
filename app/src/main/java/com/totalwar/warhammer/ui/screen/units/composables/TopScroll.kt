package com.totalwar.warhammer.ui.screen.units.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.totalwar.warhammer.R

@Composable
fun TopScroll() {
    Box(
        modifier = Modifier
            .zIndex(100f)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Image(
            painter = painterResource(id = R.drawable.roll_top),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
        )
    }
}