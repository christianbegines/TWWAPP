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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.totalwar.warhammer.R
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.ui.theme.BulletDecrease
import com.totalwar.warhammer.ui.theme.BulletIncrease

@Composable
fun UnitBullets(selectedUnit: UnitQuery.Unit) {
    for (item in selectedUnit.bullet_points.orEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item?.state.equals("positive")) {
                Image(
                    modifier = Modifier.size(15.dp).padding(top = 2.dp),
                    painter = painterResource(R.drawable.arrow_increase),
                    contentDescription = ""
                )
                Text(
                    text = "${item?.onscreen_name}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BulletIncrease
                )
            } else {
                Image(
                    modifier = Modifier.size(15.dp).padding(top = 2.dp),
                    painter = painterResource(R.drawable.arrow_decrease),
                    contentDescription = ""
                )
                Text(
                    text = "${item?.onscreen_name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BulletDecrease
                )
            }
        }
    }
}
