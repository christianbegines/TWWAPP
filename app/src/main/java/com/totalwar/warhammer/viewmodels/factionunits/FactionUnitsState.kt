package com.totalwar.warhammer.viewmodels.factionunits

import com.totalwar.warhammer.FactionUnitsQuery

sealed class FactionUnitsState {
    object Idle : FactionUnitsState()
    object Error : FactionUnitsState()
    object Loading : FactionUnitsState()

    data class Success(
        val faction: FactionUnitsQuery.Faction,
        val unitTypes: Map<String, List<FactionUnitsQuery.Unit?>>,
        val gameVersion: String
    ) : FactionUnitsState()
}
