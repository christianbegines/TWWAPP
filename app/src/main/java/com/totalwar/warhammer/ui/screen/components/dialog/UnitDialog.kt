package com.totalwar.warhammer.ui.screen.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.totalwar.warhammer.ui.screen.units.UnitScreen

@Composable
fun UnitDialog(
    id: String,
    factionId: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        UnitScreen(
            id = id,
            factionId = factionId
        )
    }
}