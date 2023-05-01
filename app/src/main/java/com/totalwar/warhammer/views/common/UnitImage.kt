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
import com.totalwar.warhammer.util.LORD_HERO
import com.totalwar.warhammer.util.formatUrlHeroLordImage
import com.totalwar.warhammer.util.formatUrlUnitImage
import com.totalwar.warhammer.util.getUnitImageUrl

@Composable
fun UnitImage(unit: UnitQuery.Unit, size: Dp, gameVersion: String) {
    UnitImageBox(url = unit.getUnitImageUrl(gameVersion), size = size)
}
@Composable
fun UnitDetailImage(unit: UnitQuery.Unit, size: Dp, gameVersion: String) {
    UnitImageDetailBox(url = unit.getUnitImageUrl(gameVersion), size = size)
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
fun UnitImageDetailBox(url: String, size: Dp) {
    Box(modifier = Modifier.padding(start = 0.dp), contentAlignment = Alignment.Center) {
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
