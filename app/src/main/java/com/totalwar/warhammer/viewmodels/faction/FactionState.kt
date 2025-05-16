package com.totalwar.warhammer.viewmodels.faction

import com.totalwar.warhammer.FactionsQuery

sealed class FactionState {
    object Idle : FactionState()
    object Error : FactionState()
    data class Loading(
        val factionList: List<FactionsQuery.Faction?>
    ) : FactionState()
    data class Success(
        val factionList: List<FactionsQuery.Faction?>,
        val gameVersion: String
    ) : FactionState()
}
