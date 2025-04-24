package com.totalwar.warhammer.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.totalwar.warhammer.R
import com.totalwar.warhammer.util.CustomToolbar
import com.totalwar.warhammer.viewmodels.armies.ArmiesState
import com.totalwar.warhammer.viewmodels.armies.ArmiesViewModel

@Composable
fun ArmiesListScreen(
    viewModel: ArmiesViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    navController: NavController
) {
    var showCustomDialogWithResult by remember { mutableStateOf(false) }
    if (showCustomDialogWithResult) {
        CreateArmyScreen(
            onDismiss = { showCustomDialogWithResult = !showCustomDialogWithResult },
            onNegativeClick = { showCustomDialogWithResult = !showCustomDialogWithResult }) {
        }
    }

    val armies by viewModel.armyList.observeAsState(
        initial = ArmiesState.Idle
    )
    val lazyGridState = rememberLazyGridState()
    Scaffold(
        topBar = {
            CustomToolbar(
                title = stringResource(id = R.string.armies),
                openDrawer
            )
        },
        content = { padding ->
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
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { showCustomDialogWithResult = true }) {
                            Text(text = "Create New Army", color = Color.White)
                        }
                    }
                }
                when (val state = armies) {
                    ArmiesState.Error -> {}
                    ArmiesState.Idle -> {}
                    ArmiesState.Loading -> {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    is ArmiesState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(vertical = 4.dp),
                            state = lazyGridState
                        ) {
                            items(state.armies) { army ->
                                Text(text = army.name)
                            }
                        }
                    }
                }
            }

        }
    )
}
