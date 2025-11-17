package com.totalwar.warhammer.ui.screen.army.create

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.totalwar.warhammer.R
import com.totalwar.warhammer.viewmodels.armies.create.CreateArmyViewModel
import com.totalwar.warhammer.viewmodels.faction.FactionState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateArmyScreen(
    viewModel: CreateArmyViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    val factionList: FactionState by viewModel.factionList.collectAsState(
        initial = FactionState.Idle
    )
    var name by remember { mutableStateOf("") }
    var factionSelected: String by remember {
        mutableStateOf("Select Faction for Army")
    }
    var factionId by remember { mutableStateOf("") }
    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.findAllFactions()
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            color = Color.Transparent,
        ) {
            when (val state = factionList) {
                is FactionState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(30.dp)
                        ) {
                            Text(
                                text = state.message,
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = onDismiss) {
                                Text("Cerrar")
                            }
                        }
                    }
                }

                FactionState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is FactionState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is FactionState.Success -> {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.unit_background),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                        Column(
                            modifier = Modifier.padding(30.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row {
                                TextField(
                                    value = name,
                                    onValueChange = { name = it },
                                    label = { Text("Army Name") }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = {
                                        expanded = !expanded
                                    }
                                ) {
                                    TextField(
                                        modifier = Modifier.menuAnchor(
                                            MenuAnchorType.PrimaryEditable,
                                            enabled = true
                                        ),
                                        value = factionSelected,
                                        onValueChange = { },
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        state.factionList.forEach { item ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(text = item.subculture?.name.toString())
                                                },
                                                onClick = {
                                                    factionSelected =
                                                        item.subculture?.name.toString()
                                                    factionId = item.key.toString()
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Button(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            viewModel.saveFaction(name, factionId, onDismiss)
                                        },
                                    ) {
                                        Text(text = "Create and Add Units", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
