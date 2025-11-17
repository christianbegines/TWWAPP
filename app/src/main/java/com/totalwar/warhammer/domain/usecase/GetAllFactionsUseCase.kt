package com.totalwar.warhammer.domain.usecase

import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.repository.IFactionRepository
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case for getting all factions
 * Encapsulates business logic for faction retrieval
 */
class GetAllFactionsUseCase @Inject constructor(
    private val factionRepository: IFactionRepository,
    private val dispatchers: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(gameVersion: String): Result<List<FactionsQuery.Faction>> {
        return withContext(dispatchers.io) {
            factionRepository.getAllFactions(gameVersion)
        }
    }
}

