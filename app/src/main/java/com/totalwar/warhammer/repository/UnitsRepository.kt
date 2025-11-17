package com.totalwar.warhammer.repository

import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.datasources.UnitsDataSource
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.safeApiCall

/**
 * Repository for managing Unit data
 * Implements IUnitsRepository for better testability
 */
class UnitsRepository(
    private val unitsDataSource: UnitsDataSource
) : IUnitsRepository {
    override suspend fun getUnit(id: String, gameVersion: String): Result<UnitQuery.Unit> {
        return safeApiCall {
            unitsDataSource.getUnitById(id, gameVersion)
                ?: throw NoSuchElementException("Unit with id $id not found")
        }
    }
}
