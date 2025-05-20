package com.totalwar.warhammer.views.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.theme.ColorOnPrimary

@Composable
fun UnitSubStat(statName: String, statValue: String, statIcon: Int?, iconSize: Dp) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 15.dp, end = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = statIcon ?: R.drawable.modifier_icon_shield),
                contentDescription = "",
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = statName,
                color = ColorOnPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
        }
        Text(
            text = statValue,
            color = ColorOnPrimary,
            fontSize = 15.sp,
            textAlign = TextAlign.Start
        )
    }
}
