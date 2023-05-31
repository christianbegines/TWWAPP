package com.totalwar.warhammer.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.totalwar.warhammer.util.TooltipUtils
import com.totalwar.warhammer.viewmodels.ability.AbilityState
import com.totalwar.warhammer.viewmodels.ability.AbilityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbilityTooltip(
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
            Surface(color = Color.Transparent) {
                Column {
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
                    Row {
                        Text(
                            text = ability.ability.tooltip.toString(),
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}
