package com.totalwar.warhammer.repository

import com.totalwar.warhammer.datasources.AbilityDataSource
import com.totalwar.warhammer.fragment.Ability

class AbilityRepository(
    private val abilityDataSource: AbilityDataSource
) {
    suspend fun getAbility(id: String, version: String): Ability? =
        abilityDataSource.getAbility(id, version)
}
