package com.totalwar.warhammer.repository

import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.util.Result

/**
 * Interface for Units Repository to allow easier testing and decoupling
 */
interface IUnitsRepository {
    suspend fun getUnit(id: String, gameVersion: String): Result<UnitQuery.Unit>
}

