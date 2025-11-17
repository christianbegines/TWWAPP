package com.totalwar.warhammer.viewmodels.armies

import com.totalwar.warhammer.viewmodels.armies.ArmyUi

/**
 * Represents different states of the Armies screen
 */
sealed class ArmiesState {
    object Idle : ArmiesState()
    data class Error(val message: String) : ArmiesState()
    object Loading : ArmiesState()
    data class Success(
        val armies: List<ArmyUi>,
        val gameVersion: String,
    ) : ArmiesState()
}