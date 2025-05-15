package com.totalwar.warhammer.views.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.totalwar.warhammer.util.formatUrlAbilityAttrIcon
import kotlinx.coroutines.CoroutineScope

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitAttribute(
    id: String,
    tooltip: String,
    size: Dp,
    gameVersion: String,
    scope: CoroutineScope
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            AttributeTooltip(tooltip)
        },
        state = rememberTooltipState(),
    ) {
        val url = formatUrlAbilityAttrIcon(id, gameVersion)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(url).diskCacheKey(url)
                .memoryCacheKey(url).crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .padding(0.dp)
                .clip(RoundedCornerShape(5.dp)),
        )
    }
}
