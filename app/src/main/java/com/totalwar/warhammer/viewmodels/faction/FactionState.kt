package com.totalwar.warhammer.viewmodels.faction

import com.totalwar.warhammer.FactionsQuery

/**
 * Represents different states of the Faction screen
 */
sealed class FactionState {
    object Idle : FactionState()
    data class Error(val message: String) : FactionState()
    data class Loading(
        val factionList: List<FactionsQuery.Faction>
    ) : FactionState()
    data class Success(
        val factionList: List<FactionsQuery.Faction>,
        val gameVersion: String
    ) : FactionState()
}
