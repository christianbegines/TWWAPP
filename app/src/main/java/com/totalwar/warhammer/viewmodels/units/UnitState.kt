package com.totalwar.warhammer.viewmodels.units

import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.UnitQuery

/**
 * Represents different states of the Unit screen
 */
sealed class UnitState() {
    object Idle : UnitState()
    data class Error(val message: String) : UnitState()
    object Loading : UnitState()
    data class Success(
        val unit: UnitQuery.Unit,
        val faction: FactionsQuery.Faction,
        val gameVersion: String
    ) : UnitState()
}
