package com.totalwar.warhammer.repository

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.datasources.FactionUnitsDataSource
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.safeApiCall

/**
 * Repository for managing Faction Units data
 * Implements IFactionUnitsRepository for better testability
 */
class FactionUnitsRepository(
    private val factionUnitsDataSource: FactionUnitsDataSource
) : IFactionUnitsRepository {
    override suspend fun findUnitsByFaction(id: String, gameVersion: String): Result<FactionUnitsQuery.Faction> {
        return safeApiCall {
            factionUnitsDataSource.getUnitsFaction(id, gameVersion)
                ?: throw NoSuchElementException("Faction with id $id not found")
        }
    }
}
