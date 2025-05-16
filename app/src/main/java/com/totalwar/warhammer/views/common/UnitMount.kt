package com.totalwar.warhammer.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.totalwar.warhammer.R
import com.totalwar.warhammer.UnitQuery
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
    val tooltipState = rememberTooltipState()
    val iconName = battleMounts.firstOrNull { it?.mounted_unit == unit }?.icon_name
    val coroutineScope = rememberCoroutineScope()

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            RichTooltip {
                BattleMounts(
                    gameVersion = gameVersion,
                    faction = faction,
                    battleMounts = battleMounts,
                    viewModel = viewModel
                )
            }
        },
        state = tooltipState,
    ) {
        if (iconName != null && mount != null) {
            val url = formatUrlMountImage(gameVersion, iconName)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = "Mount image",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        coroutineScope.launch {
                            tooltipState.show()
                        }
                    }
                    .clip(RoundedCornerShape(5.dp))
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.icon_cross),
                contentDescription = "No mount available",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        coroutineScope.launch {
                            tooltipState.show()
                        }
                    },
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}