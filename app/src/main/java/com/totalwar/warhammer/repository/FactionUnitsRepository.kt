package com.totalwar.warhammer.repository

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.datasources.FactionUnitsDataSource

class FactionUnitsRepository(
    private val factionUnitsDataSource: FactionUnitsDataSource
) {

    suspend fun findUnitsByFaction(id: String, gameVersion: String): FactionUnitsQuery.Faction? {
        return factionUnitsDataSource.getUnitsFaction(id, gameVersion)
    }
}
