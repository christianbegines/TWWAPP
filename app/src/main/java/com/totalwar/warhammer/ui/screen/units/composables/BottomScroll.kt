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
fun BottomScroll() {
    Box(contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(id = R.drawable.roll_bottom),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(100f),
            alignment = Alignment.BottomCenter,
            contentScale = ContentScale.Crop,
        )
    }
}