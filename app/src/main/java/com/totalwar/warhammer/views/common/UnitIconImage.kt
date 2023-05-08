package com.totalwar.warhammer.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.R
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.util.formatUrlUnitIcon
import com.totalwar.warhammer.util.isLordExclusive

@Composable
fun UnitIconImage(unit: UnitQuery.Unit, size: Dp, gameVersion: String) {
    val url = formatUrlUnitIcon(unit.ui_unit_group?.icon.orEmpty(), gameVersion)
    UnitIconImageBox(
        url = url,
        size = size,
        unit.unit_sets?.any { it?.special_category?.contains("renown") == true }?.or(false) == true,
        isCampaignExclusive = unit.isLordExclusive()
    )
}

@Composable
fun UnitIconImageBox(url: String, size: Dp, isRenown: Boolean, isCampaignExclusive: Boolean) {
    var sizeForIcon = size
    var paddingTopForIcon = 5.dp
    var paddingBottomForIcon = 2.dp
    var paddingLeftForIcon = 0.dp
    var paddingRightForIcon = 0.dp
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(
                if (isRenown) {
                    sizeForIcon += 20.dp
                    paddingBottomForIcon = 0.dp
                    R.drawable.unit_cat_holder_round_renown
                } else if (isCampaignExclusive) {
                    sizeForIcon += 20.dp
                    paddingBottomForIcon = 0.dp
                    R.drawable.unit_cat_holder_round_elector
                } else {
                    sizeForIcon += 10.dp
                    paddingTopForIcon = 3.dp
                    paddingLeftForIcon = 0.dp
                    paddingRightForIcon = 0.dp
                    R.drawable.unit_cat_holder_round
                }
            ),
            contentDescription = "",
            modifier = Modifier
                .size(sizeForIcon)
                .zIndex(100f)
                .align(Alignment.CenterStart),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = rememberAsyncImagePainter(url),
            contentDescription = null,
            modifier = Modifier
                .size(size + 5.dp)
                .zIndex(100f)
                .padding(
                    top = paddingTopForIcon,
                    end = paddingRightForIcon,
                    start = paddingLeftForIcon,
                    bottom = paddingBottomForIcon
                )
        )
    }
}
