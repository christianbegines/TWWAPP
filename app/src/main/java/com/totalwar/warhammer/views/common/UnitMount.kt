package com.totalwar.warhammer.views.common

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.totalwar.warhammer.R
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.ui.theme.SecondaryColor
import com.totalwar.warhammer.util.formatUrlMountImage
import com.totalwar.warhammer.viewmodels.units.UnitViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitMount(
    faction: String,
    unit: String,
    scope: CoroutineScope,
    mount: UnitQuery.Mount?,
    gameVersion: String,
    battleMounts: List<UnitQuery.Battle_mount?>,
    viewModel: UnitViewModel
) {
    val tooltipState = remember { RichTooltipState() }
    RichTooltipBox(
        text = {
            BattleMounts(
                gameVersion = gameVersion,
                faction = faction,
                battleMounts = battleMounts,
                viewModel = viewModel
            )
        },
        action = { },
        colors = RichTooltipColors(
            containerColor = SecondaryColor,
            contentColor = Color.Transparent,
            titleContentColor = Color.Transparent,
            actionContentColor = Color.Transparent,
        ),
        tooltipState = tooltipState,
    ) {
        if (mount != null) {
            val url = formatUrlMountImage(gameVersion,
                battleMounts.firstOrNull { it?.mounted_unit == unit }?.icon_name.toString())
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .diskCacheKey(url)
                    .memoryCacheKey(url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .tooltipAnchor()
                    .size(50.dp)
                    .padding(0.dp)
                    .clickable {
                        scope.launch { tooltipState.show() }
                    }
                    .clip(
                        RoundedCornerShape(5.dp),
                    ))
        } else {
            Image(
                painter = painterResource(id = R.drawable.icon_cross),
                contentDescription = "",
                modifier = Modifier
                    .tooltipAnchor()
                    .size(50.dp)
                    .padding(0.dp)
                    .clickable {
                        scope.launch { tooltipState.show() }
                    }
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.FillBounds,
            )
        }

    }
}