package com.totalwar.warhammer.domain.usecase

import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.repository.IUnitsRepository
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case for getting a unit by ID
 * Encapsulates business logic for unit retrieval
 */
class GetUnitByIdUseCase @Inject constructor(
    private val unitsRepository: IUnitsRepository,
    private val dispatchers: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(id: String, gameVersion: String): Result<UnitQuery.Unit> {
        return withContext(dispatchers.io) {
            unitsRepository.getUnit(id, gameVersion)
        }
    }
}

