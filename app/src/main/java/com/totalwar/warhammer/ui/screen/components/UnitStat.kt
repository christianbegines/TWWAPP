package com.totalwar.warhammer.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.totalwar.warhammer.R
import com.totalwar.warhammer.fragment.Contact_phase
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.util.formatUrlAbilityTypeImageEffect

@Composable
fun UnitStat(
    gameVersion: String? = null,
    statName: String,
    statValue: String,
    statIcon: Int,
    ignition: Boolean = false,
    magical: Boolean = false,
    contact: Contact_phase? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row {
            Image(
                painter = painterResource(id = statIcon),
                contentDescription = "",
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = statName,
                color = ColorOnPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically,){
            if(magical){
                Image(
                    painter = painterResource(id = R.drawable.modifier_icon_magical),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
            if(ignition){
                Image(
                    painter = painterResource(id = R.drawable.modifier_icon_flaming),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
            if(contact != null){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            formatUrlAbilityTypeImageEffect(
                                gameVersion.orEmpty(),
                                contact.id.orEmpty()
                            )
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .clip(RoundedCornerShape(5.dp))
                )
            }
            Text(
                text = statValue,
                color = ColorOnPrimary,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
            )
        }

    }
}
