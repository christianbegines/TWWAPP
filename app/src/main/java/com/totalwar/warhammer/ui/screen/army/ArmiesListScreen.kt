package com.totalwar.warhammer.ui.screen.army

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.totalwar.warhammer.R
import com.totalwar.warhammer.ui.screen.army.composables.ArmiesContent
import com.totalwar.warhammer.ui.screen.army.composables.CreateArmyButton
import com.totalwar.warhammer.ui.screen.army.create.CreateArmyScreen
import com.totalwar.warhammer.ui.screen.components.CustomToolbar
import com.totalwar.warhammer.viewmodels.armies.ArmiesState
import com.totalwar.warhammer.viewmodels.armies.ArmiesViewModel

@Composable
fun ArmiesListScreen(
    viewModel: ArmiesViewModel = hiltViewModel(),
    openDrawer: () -> Unit
) {
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    if (showCustomDialogWithResult) {
        CreateArmyScreen(
            onDismiss = { showCustomDialogWithResult = false; viewModel.findAllArmies() },
            onNegativeClick = { showCustomDialogWithResult = false; viewModel.findAllArmies() }
        ) {}
    }

    val armies by viewModel.armyState.collectAsState(initial = ArmiesState.Idle)
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(armies) {
        if (armies is ArmiesState.Idle) viewModel.findAllArmies()
        if (armies is ArmiesState.Success) {
            val list = (armies as ArmiesState.Success).armies
            if (list.isNotEmpty()) lazyGridState.animateScrollToItem(list.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            CustomToolbar(
                title = stringResource(id = R.string.armies),
                openDrawer
            )
        }
    ) { padding ->
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.backgroundttw),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                CreateArmyButton { showCustomDialogWithResult = true }
                ArmiesContent(armies, lazyGridState, onRefresh = { viewModel.findAllArmies() })
            }
        }
    }
}
