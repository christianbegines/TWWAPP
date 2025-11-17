package com.totalwar.warhammer.ui.screen.units

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.screen.components.CustomAncillaryAbilities
import com.totalwar.warhammer.ui.screen.components.UnitAbilitiesRow
import com.totalwar.warhammer.ui.screen.components.UnitAttributesRow
import com.totalwar.warhammer.ui.screen.components.UnitSpecialAbilitiesRow
import com.totalwar.warhammer.ui.screen.units.composables.BottomScroll
import com.totalwar.warhammer.ui.screen.units.composables.TopScroll
import com.totalwar.warhammer.ui.screen.units.composables.UnitBulletsSection
import com.totalwar.warhammer.ui.screen.units.composables.UnitImageAndName
import com.totalwar.warhammer.ui.screen.units.composables.UnitMountAndIconRow
import com.totalwar.warhammer.ui.screen.units.composables.UnitStatsSection
import com.totalwar.warhammer.ui.theme.BulletBackground
import com.totalwar.warhammer.util.Constants
import com.totalwar.warhammer.util.isRenown
import com.totalwar.warhammer.viewmodels.units.UnitState
import com.totalwar.warhammer.viewmodels.units.UnitViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnitScreen(
    viewModel: UnitViewModel = hiltViewModel(),
    id: String,
    factionId: String,
) {
    val unit: UnitState by viewModel.unitState.collectAsState(initial = UnitState.Idle)

    LaunchedEffect(id, factionId) {
        viewModel.findUnitById(id, factionId)
    }
    Scaffold(
        modifier = Modifier.padding(5.dp),
        containerColor = Color.Transparent,
    ) { contentPadding ->
        when (val state = unit) {
            is UnitState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(Constants.UI.PADDING_LARGE)
                    ) {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            is UnitState.Idle,
            is UnitState.Loading,
            -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UnitState.Success -> {
                val selectedUnit = state.unit
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                ) {
                    TopScroll()
                    Box(
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.unit_background),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                        Column(
                            modifier = Modifier
                                .padding(
                                    top = if (selectedUnit.isRenown()) 10.dp else 20.dp,
                                    bottom = 20.dp,
                                )
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,

                            ) {
                            UnitImageAndName(state)
                            UnitMountAndIconRow(factionId, state, viewModel)
                            UnitBulletsSection(state.unit)
                            UnitStatsSection(state)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .border(1.dp, BulletBackground)
                                    .background(
                                        Color.Transparent.copy(0.1f),
                                    )
                            ) {
                                UnitAttributesRow(state)
                                UnitAbilitiesRow(state)
                                CustomAncillaryAbilities(state)
                                UnitSpecialAbilitiesRow(state)
                            }
                        }
                    }
                    BottomScroll()
                }
            }
        }
    }
}
