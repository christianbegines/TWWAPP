package com.totalwar.warhammer.views.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.totalwar.warhammer.util.formatUrlAbilityAttrIcon

@Composable
fun UnitAbilityAttrIconImage(
    params: String,
    size: Dp,
    gameVersion: String,
    tooltip: String
) {
    UnitAbilityAttrImageBox(
        url = formatUrlAbilityAttrIcon(params, gameVersion),
        size = size,
        tooltip = tooltip
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitAbilityAttrImageBox(url: String, size: Dp, tooltip: String) {
    RichTooltipBox(
        title = { Text(tooltip) },
        action = {},
        text = {
            Text(tooltip)
        }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(url).diskCacheKey(url)
                .memoryCacheKey(url).crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.tooltipAnchor().size(size).padding(1.dp)
                .clip(RoundedCornerShape(5.dp))
        )
    }
}

@Composable
fun UnitAbilityAttrImageBox() {
}
