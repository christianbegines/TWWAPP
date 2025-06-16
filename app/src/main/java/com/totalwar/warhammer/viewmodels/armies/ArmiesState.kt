package com.totalwar.warhammer.viewmodels.armies

import com.totalwar.warhammer.database.army.Army
import com.totalwar.warhammer.type.GameVersion

//import com.totalwar.warhammer.database.army.Army

sealed class ArmiesState {
    object Idle : ArmiesState()
    object Error : ArmiesState()
    object Loading : ArmiesState()
    data class Success(
        val armies: List<ArmyUi>,
        val gameVersion: String,
    ) : ArmiesState()
}