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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.datastore.core.DataStore
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.totalwar.warhammer.R
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.CustomToolbarWithBackArrow
import com.totalwar.warhammer.viewmodels.AppViewModel

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
    settings?.let { viewModel.findUnitById(id, it.gameVersion) }

    Scaffold(
        topBar = {
            CustomToolbarWithBackArrow(title = "Unit Details", navController = navController)
        }
    ) {
        if (selectedUnit != null) {
            val url =
                if (selectedUnit.caste != "Lord" && selectedUnit.caste != "Hero") {
                    selectedUnit.land_unit?.variant?.unit_card_url.let {
                        "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/327635228256759215/ui/units/icons/$it.png"
                    }
                } else {
                    selectedUnit.custom_battle_permissions?.first()?.general_portrait?.let {
                        "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/327635228256759215/${
                        it.replace(
                            "portholes",
                            "units"
                        )
                        }"
                    }
                }
            Surface(
                modifier = Modifier.fillMaxSize().padding(10.dp)
            ) {
                Box(
                    modifier = Modifier.zIndex(100f).fillMaxWidth(),
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
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.unit_card_frame_plain),
                                contentDescription = "",
                                modifier = Modifier.width(140.dp).height(280.dp).zIndex(100f),
                                contentScale = ContentScale.FillBounds
                            )
                            Image(
                                painter = rememberAsyncImagePainter(url),
                                contentDescription = null,
                                modifier = Modifier.size(280.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${selectedUnit.land_unit?.onscreen_name}",
                            fontSize = 25.sp,
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
                        modifier = Modifier.fillMaxWidth().zIndex(100f),
                        alignment = Alignment.BottomCenter,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
