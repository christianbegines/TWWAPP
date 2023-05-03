package com.totalwar.warhammer.repository

import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.datasources.FactionDataSource

class FactionRepository(
    private val factionDataSource: FactionDataSource
) {
    suspend fun getAllFactions(version: String): List<FactionsQuery.Faction?> {
        return factionDataSource.getFactions(version)
    }
}
