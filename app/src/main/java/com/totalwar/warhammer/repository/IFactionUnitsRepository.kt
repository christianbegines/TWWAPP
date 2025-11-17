package com.totalwar.warhammer.repository

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.util.Result

/**
 * Interface for FactionUnits Repository to allow easier testing and decoupling
 */
interface IFactionUnitsRepository {
    suspend fun findUnitsByFaction(id: String, gameVersion: String): Result<FactionUnitsQuery.Faction>
}

