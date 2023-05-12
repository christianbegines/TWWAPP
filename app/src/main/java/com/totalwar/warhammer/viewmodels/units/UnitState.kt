package com.totalwar.warhammer.viewmodels.units

import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.UnitQuery

sealed class UnitState() {
    object Idle : UnitState()
    object Error : UnitState()
    object Loading : UnitState()
    data class Success(
        val unit: UnitQuery.Unit,
        val faction: FactionsQuery.Faction,
        val gameVersion: String
    ) : UnitState()
}
