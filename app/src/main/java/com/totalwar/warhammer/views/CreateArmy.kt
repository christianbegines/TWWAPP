package com.totalwar.warhammer.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateArmy(
    viewModel: CreateArmyViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    val factionList: FactionState by viewModel.factionList.observeAsState(
        initial = FactionState.Idle
    )
    var name by remember { mutableStateOf("") }
    var factionId by remember { mutableStateOf("") }
    var expanded by remember {
        mutableStateOf(false)
    }

    viewModel.findAllFactions()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            color = Color.Transparent,
        ) {
            when (val state = factionList) {
                FactionState.Error -> {}
                FactionState.Idle -> {}
                is FactionState.Loading -> {}
                is FactionState.Success -> {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.unit_background),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                        Column(modifier = Modifier.padding(30.dp),
                                verticalArrangement = Arrangement.Center)    {
                            Row {
                                TextField(
                                    value = name,
                                    onValueChange = { name = it },
                                    label = { Text("Army Name") }
                                )
                            }
                            Row {
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = {
                                        expanded = !expanded
                                    }
                                ) {
                                    TextField(
                                        value = "Select the faction",
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        state.factionList.forEach { item ->
                                            DropdownMenuItem(
                                                text = { Text(text = item?.screen_name.toString()) },
                                                onClick = {
                                                    factionId = item?.subculture?.name.toString()
                                                    expanded = false
                                                }
                                            )
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
}


