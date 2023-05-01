package com.totalwar.warhammer.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.util.formatUrlAbilityAttrIcon

@Composable
fun UnitAbilityAttrIconImage(params: String, size: Dp, gameVersion: String) {
    UnitAbilityAttrImageBox(url = formatUrlAbilityAttrIcon(params, gameVersion), size = size)
}

@Composable
fun UnitAbilityAttrImageBox(url: String, size: Dp) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .padding(1.dp)
            .clip(RoundedCornerShape(5.dp))
    )
}
