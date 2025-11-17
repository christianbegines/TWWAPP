package com.totalwar.warhammer.domain.usecase

import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.repository.IFactionUnitsRepository
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case for getting units by faction
 * Encapsulates business logic for faction units retrieval
 */
class GetFactionUnitsUseCase @Inject constructor(
    private val factionUnitsRepository: IFactionUnitsRepository,
    private val dispatchers: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(factionId: String, gameVersion: String): Result<FactionUnitsQuery.Faction> {
        return withContext(dispatchers.io) {
            factionUnitsRepository.findUnitsByFaction(factionId, gameVersion)
        }
    }
}

