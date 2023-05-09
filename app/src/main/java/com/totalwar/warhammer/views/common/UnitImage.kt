package com.totalwar.warhammer.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.R
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.util.getUnitImageUrl
import com.totalwar.warhammer.util.isRenown

@Composable
fun UnitImage(unit: UnitQuery.Unit, size: Dp, gameVersion: String) {
    UnitImageBox(url = unit.getUnitImageUrl(gameVersion), size = size)
}

@Composable
fun UnitDetailImage(unit: UnitQuery.Unit, size: Dp, gameVersion: String) {
    UnitImageDetailBox(url = unit.getUnitImageUrl(gameVersion), size = size, isRenown = unit.isRenown())
}

@Composable
fun UnitImageBox(url: String, size: Dp) {
    Surface(modifier = Modifier.padding(start = 5.dp)) {
        Image(
            painter = painterResource(id = R.drawable.unit_card_frame_plain),
            contentDescription = "",
            modifier = Modifier
                .width(size / 2)
                .height(size)
                .zIndex(100f)
                .clip(RoundedCornerShape(1.dp)),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = null,
            modifier = Modifier
                .width(size / 2)
                .height(size)
                .clip(RoundedCornerShape(5.dp))
        )
    }
}

@Composable
fun UnitImageDetailBox(url: String, size: Dp, isRenown: Boolean) {
    Box(modifier = Modifier.padding(start = 0.dp), contentAlignment = Alignment.Center) {
        if (isRenown) {
            Image(
                painter = painterResource(id = R.drawable.unit_renown_unlocked_frame__1_),
                contentDescription = "",
                modifier = Modifier
                    .width(size + 4.dp)
                    .padding(bottom = 15.dp)
                    .height(size + 60.dp)
                    .zIndex(200f)
                    .clip(RoundedCornerShape(1.dp)),
                contentScale = ContentScale.FillBounds
            )
        }
        Image(
            painter = painterResource(id = R.drawable.unit_card_frame_plain),
            contentDescription = "",
            modifier = Modifier
                .width(size / 2)
                .height(size)
                .zIndex(100f)
                .clip(RoundedCornerShape(1.dp)),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = null,
            modifier = Modifier
                .width(size / 2)
                .height(size)
                .clip(RoundedCornerShape(5.dp))
        )
    }
}
