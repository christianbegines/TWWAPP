package com.totalwar.warhammer.repository

import com.totalwar.warhammer.datasources.AbilityDataSource
import com.totalwar.warhammer.fragment.Ability
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.safeApiCall

/**
 * Repository for managing Ability data
 * Implements IAbilityRepository for better testability
 */
class AbilityRepository(
    private val abilityDataSource: AbilityDataSource
) : IAbilityRepository {
    override suspend fun getAbility(id: String, version: String): Result<Ability> {
        return safeApiCall {
            abilityDataSource.getAbility(id, version)
                ?: throw NoSuchElementException("Ability with id $id not found")
        }
    }
}
