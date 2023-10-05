package com.totalwar.warhammer.viewmodels.armies.create

import com.totalwar.warhammer.FactionsQuery

sealed class CreateArmyState {
    object Idle : CreateArmyState()
    object Error : CreateArmyState()
    object Loading : CreateArmyState()
    data class Success(
        val factionList: List<FactionsQuery.Faction?>
    ) : CreateArmyState()
}
