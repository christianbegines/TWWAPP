package com.totalwar.warhammer.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.totalwar.warhammer.util.formatUrlBattleMountImage
import com.totalwar.warhammer.viewmodels.units.UnitViewModel

@Composable
fun BattleMounts(
    gameVersion: String,
    faction: String,
    battleMounts: List<UnitQuery.Battle_mount?>,
    viewModel: UnitViewModel
) {
    Column{
        LazyColumn(
            modifier = Modifier.padding(0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            items(battleMounts) {
                val url = formatUrlBattleMountImage(gameVersion, it?.icon_name.toString())
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .diskCacheKey(url)
                        .memoryCacheKey(url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clickable {
                            viewModel.findUnitById(it?.mounted_unit.toString(),faction)
                        }
                        .clip(RoundedCornerShape(5.dp)),
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.icon_cross),
            contentDescription = "",
            modifier = Modifier
                .size(70.dp)
                .padding(0.dp)
                .clickable {
                    viewModel.findUnitById(battleMounts.firstOrNull()?.base_unit.toString(),faction)
                }
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.FillBounds,
        )
    }
}