package com.totalwar.warhammer.domain.usecase

import com.totalwar.warhammer.fragment.Ability
import com.totalwar.warhammer.repository.IAbilityRepository
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case for getting an ability by ID
 * Encapsulates business logic for ability retrieval
 */
class GetAbilityUseCase @Inject constructor(
    private val abilityRepository: IAbilityRepository,
    private val dispatchers: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(id: String, gameVersion: String): Result<Ability> {
        return withContext(dispatchers.io) {
            abilityRepository.getAbility(id, gameVersion)
        }
    }
}

