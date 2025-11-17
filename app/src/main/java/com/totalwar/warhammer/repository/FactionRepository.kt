package com.totalwar.warhammer.repository

import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.datasources.FactionDataSource
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.safeApiCall

/**
 * Repository for managing Faction data
 * Implements IFactionRepository for better testability
 */
class FactionRepository(
    private val factionDataSource: FactionDataSource
) : IFactionRepository {
    override suspend fun getAllFactions(version: String): Result<List<FactionsQuery.Faction>> {
        return safeApiCall {
            factionDataSource.getFactions(version).filterNotNull()
        }
    }
}
