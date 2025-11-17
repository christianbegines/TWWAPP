package com.totalwar.warhammer.viewmodels.factionunits

import com.totalwar.warhammer.FactionUnitsQuery

/**
 * Represents different states of the Faction Units screen
 */
sealed class FactionUnitsState {
    object Idle : FactionUnitsState()
    data class Error(val message: String) : FactionUnitsState()
    object Loading : FactionUnitsState()

    data class Success(
        val faction: FactionUnitsQuery.Faction,
        val unitTypes: Map<String, List<FactionUnitsQuery.Unit?>>,
        val gameVersion: String
    ) : FactionUnitsState()
}
