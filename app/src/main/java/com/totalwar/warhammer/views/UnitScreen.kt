package com.totalwar.warhammer.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.theme.BulletBackground
import com.totalwar.warhammer.ui.theme.ColorOnPrimary
import com.totalwar.warhammer.viewmodels.units.UnitState
import com.totalwar.warhammer.viewmodels.units.UnitViewModel
import com.totalwar.warhammer.views.common.UnitBullets
import com.totalwar.warhammer.views.common.UnitDetailImage
import com.totalwar.warhammer.views.common.UnitIconImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnitScreen(
    viewModel: UnitViewModel = hiltViewModel(),
    id: String
) {
    val unit: UnitState by viewModel.unit.observeAsState(initial = UnitState.Idle)
    viewModel.findUnitById(id)
    Scaffold(
        modifier = Modifier.padding(5.dp),
        backgroundColor = Color.Transparent
    ) {
        when (val state = unit) {
            is UnitState.Error -> {}
            is UnitState.Idle,
            is UnitState.Loading -> {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UnitState.Success -> {
                val selectedUnit = state.unit
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.fillMaxSize().padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .zIndex(100f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.roll_top),
                            contentDescription = "",
                            modifier = Modifier.fillMaxWidth(),
                            alignment = Alignment.TopCenter,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.unit_background),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                        Column(
                            modifier = Modifier.padding(30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            UnitDetailImage(
                                unit = selectedUnit,
                                size = 170.dp,
                                state.gameVersion
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "${selectedUnit.land_unit?.onscreen_name}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                UnitIconImage(
                                    unit = selectedUnit,
                                    size = 20.dp,
                                    gameVersion = state.gameVersion
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "${selectedUnit.ui_unit_group?.name}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                Column(
                                    modifier = Modifier.border(1.dp, BulletBackground).background(
                                        Color.Transparent.copy(0.1f)
                                    ),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    UnitBullets(selectedUnit)
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_treasury),
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = selectedUnit.multiplayer_cost.toString(),
                                    color = ColorOnPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Damage:${selectedUnit.land_unit?.primary_melee_weapon?.damage} " +
                                    "| AP:${selectedUnit.land_unit?.primary_melee_weapon?.ap_damage}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Box(contentAlignment = Alignment.BottomCenter) {
                        Image(
                            painter = painterResource(id = R.drawable.roll_bottom),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .zIndex(100f),
                            alignment = Alignment.BottomCenter,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
