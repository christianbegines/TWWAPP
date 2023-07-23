package com.totalwar.warhammer.viewmodels.armies

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.database.army.Army

sealed class ArmiesState {
    object Idle : ArmiesState()
    object Error : ArmiesState()
    object Loading : ArmiesState()
    data class Success(
        val armies: List<Army>
    ) : ArmiesState()
}