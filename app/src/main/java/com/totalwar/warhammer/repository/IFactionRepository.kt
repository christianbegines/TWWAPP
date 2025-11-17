package com.totalwar.warhammer.repository

import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.util.Result

/**
 * Interface for Faction Repository to allow easier testing and decoupling
 */
interface IFactionRepository {
    suspend fun getAllFactions(version: String): Result<List<FactionsQuery.Faction>>
}

