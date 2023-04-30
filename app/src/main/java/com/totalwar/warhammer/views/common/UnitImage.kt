package com.totalwar.warhammer.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Composable
fun UnitImage(unit: UnitQuery.Unit, size: Dp, gameVersion: String) {
    val url = if (LORD_HERO.contains(unit.caste.orEmpty())) {
        unit.custom_battle_permissions?.first()?.general_portrait.let {
            formatUrlHeroLordImage(it.orEmpty(), gameVersion)
        }
    } else {
        unit.land_unit?.variant?.unit_card_url.let {
            formatUrlUnitImage(it.orEmpty(), gameVersion)
        }
    }
    UnitImageBox(url = url, size = size)
}

@Composable
fun UnitImageBox(url: String, size: Dp) {
    Surface( modifier = Modifier.padding(start = 5.dp)) {
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
                .clip(RoundedCornerShape(5.dp)),

        )
    }
}
