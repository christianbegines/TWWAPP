package com.totalwar.warhammer.views.common

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.RichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.totalwar.warhammer.ui.theme.Grey
import com.totalwar.warhammer.util.formatUrlAbilityAttrIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    val tooltipState = remember { RichTooltipState() }
    RichTooltipBox(
        action = {},
        text = {
            AttributeTooltip(tooltip)
        },
        colors = RichTooltipColors(
            containerColor = Grey,
            contentColor = Color.Transparent,
            titleContentColor = Color.Transparent,
            actionContentColor = Color.Transparent
        ),
        tooltipState = tooltipState
    ) {
        val url = formatUrlAbilityAttrIcon(id, gameVersion)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(url).diskCacheKey(url)
                .memoryCacheKey(url).crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.tooltipAnchor().size(size).padding(0.dp).clickable {
                scope.launch { tooltipState.show() }
            }.clip(RoundedCornerShape(5.dp))
        )
    }
}
