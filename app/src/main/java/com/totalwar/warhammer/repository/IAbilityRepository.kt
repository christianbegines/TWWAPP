package com.totalwar.warhammer.repository

import com.totalwar.warhammer.fragment.Ability
import com.totalwar.warhammer.util.Result

/**
 * Interface for Ability Repository to allow easier testing and decoupling
 */
interface IAbilityRepository {
    suspend fun getAbility(id: String, version: String): Result<Ability>
}

