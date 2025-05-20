package com.totalwar.warhammer.views.composables.tootlips.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.totalwar.warhammer.R
import com.totalwar.warhammer.util.TooltipUtils
import com.totalwar.warhammer.util.formatUrlAbilityTypeImage
import com.totalwar.warhammer.util.getAbilityDuration
import com.totalwar.warhammer.util.getAbilityIcon
import com.totalwar.warhammer.util.getAbilityType
import com.totalwar.warhammer.util.getTarget
import com.totalwar.warhammer.viewmodels.ability.AbilityState
import com.totalwar.warhammer.viewmodels.ability.AbilityViewModel

@Composable
fun AbilityTooltipContent(
    viewModel: AbilityViewModel = hiltViewModel(),
    id: String
) {
    val state: AbilityState by viewModel.state.observeAsState(initial = AbilityState.Idle)
    LaunchedEffect(Unit) {
        viewModel.findAbility(id)
    }

    when (val ability = state) {
        AbilityState.Error,
        AbilityState.Idle,
        AbilityState.Loading -> Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is AbilityState.Success -> {
            val target = ability.ability.unit_special_ability?.getTarget()
            Surface(color = Color.Transparent) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        Box(contentAlignment = Alignment.Center) {
                            TooltipUtils.getTittleResource(ability.ability.uniqueness)
                                ?.let { painterResource(it) }?.let {
                                    Image(
                                        modifier = Modifier.fillMaxWidth(),
                                        alignment = Alignment.TopCenter,
                                        contentScale = ContentScale.Crop,
                                        painter = it,
                                        contentDescription = ""
                                    )
                                }

                            Text(
                                text = ability.ability.name.toString(),
                                color = Color.White
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = ability.ability.tooltip.toString(),
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ability.ability.type?.onscreen_name?.let { name ->
                            Column(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = "Type:",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(
                                            formatUrlAbilityTypeImage(
                                                ability.gameVersion,
                                                name.getAbilityIcon().toString()
                                            )
                                        )
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                )
                            }
                            Column(
                                modifier = Modifier.weight(4f)
                            ) {
                                Text(
                                    text = name.getAbilityType().toString(),
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(2f)
                        ) {
                            Text(
                                text = "Duration:",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            modifier = Modifier.weight(4f)
                        ) {
                            Text(
                                text = ability.ability.getAbilityDuration(),
                                color = Color.White,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    if (target?.isNotEmpty() == true) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = "Target:",
                                    color = Color.White,
                                    fontWeight = Bold,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column(
                                modifier = Modifier.weight(4f)
                            ) {
                                Text(
                                    text = target,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            val range =
                                ability.ability.unit_special_ability.target_intercept_range ?: 0
                            if (range > 0) {
                                Column(
                                    modifier = Modifier.weight(3f)
                                ) {
                                    Text(
                                        text = "Distance:",
                                        fontWeight = Bold,
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Image(
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = R.drawable.icon_distance_to_target),
                                        contentDescription = ""
                                    )
                                }
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = range.toString(),
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (ability.ability.additional_ui_effects?.isNotEmpty() == true ||
                        ability.ability.phases?.first()?.contact_phase_ability?.stat_effects?.isNotEmpty() == true
                    )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = "Effects:",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column(
                                modifier = Modifier.weight(6f)
                            ) {
                                ability.ability.additional_ui_effects?.forEach { additional ->
                                    Text(
                                        text = additional?.localised_text.toString(),
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }

                            }
                        }
                }
            }
        }
    }
}
