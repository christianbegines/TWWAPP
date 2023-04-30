package com.totalwar.warhammer.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.datastore.core.DataStore
import androidx.navigation.NavHostController
import com.totalwar.warhammer.R
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.CustomToolbarWithBackArrow
import com.totalwar.warhammer.viewmodels.AppViewModel
import com.totalwar.warhammer.views.common.UnitImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnitScreen(
    viewModel: AppViewModel,
    dataStore: DataStore<Settings>,
    navController: NavHostController,
    id: String
) {
    val settings: Settings? by dataStore.data.collectAsState(
        initial = null
    )
    val selectedUnit = viewModel.unit.observeAsState().value
    val gameVersion: String = settings?.let { it.gameVersion }.orEmpty()
    viewModel.findUnitById(id, gameVersion)
    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(title = "Unit Details", navController = navController)
        }
    ) {
        if (selectedUnit != null) {
            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxSize().paint(
                    painter = painterResource(R.drawable.backgroundttw),
                    contentScale = ContentScale.FillBounds
                ).padding(10.dp)
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
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
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
                        UnitImage(unit = selectedUnit, size = 280.dp, gameVersion)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${selectedUnit.land_unit?.onscreen_name}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "(${selectedUnit.ui_unit_group?.name})",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Cost: ${selectedUnit.multiplayer_cost}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
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
