package com.totalwar.warhammer.repository

import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.datasources.UnitsDataSource

class UnitsRepository(
    private val unitsDataSource: UnitsDataSource
) {
    suspend fun getUnit(id: String, gameVersion: String): UnitQuery.Unit? {
        return unitsDataSource.getUnitById(id, gameVersion)
    }
}
