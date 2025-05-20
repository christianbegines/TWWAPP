package com.totalwar.warhammer.ui.screen.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.totalwar.warhammer.R

@Composable
fun Header(title: String) {
    Box(
        modifier = Modifier
            .zIndex(100f)
            .padding(top = 5.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.unit_background),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillBounds
        )
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
    }
}