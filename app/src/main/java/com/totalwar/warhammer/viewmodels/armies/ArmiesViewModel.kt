package com.totalwar.warhammer.viewmodels.armies

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.domain.usecase.GetAllFactionsUseCase
import com.totalwar.warhammer.repository.ArmyRepository
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.Logger
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel para gestionar el estado de las armadas del usuario
 * Obtiene armadas del repositorio local y las enriquece con información de facciones
 */
@HiltViewModel
class ArmiesViewModel @Inject constructor(
    private val armyRepository: ArmyRepository,
    private val getAllFactionsUseCase: GetAllFactionsUseCase,
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val dataStore: DataStore<Settings>
) : ViewModel() {

    private val _armyState = MutableStateFlow<ArmiesState>(ArmiesState.Idle)
    val armyState: StateFlow<ArmiesState> = _armyState.asStateFlow()

    fun findAllArmies() {
        viewModelScope.launch {
            _armyState.value = ArmiesState.Loading
            withContext(dispatcherProvider.io) {
                try {
                    val gameVersion = dataStore.data.first().gameVersion

                    // Obtener facciones usando el UseCase
                    when (val factionsResult = getAllFactionsUseCase(gameVersion)) {
                        is Result.Success -> {
                            val factions = factionsResult.data

                            // Enriquecer armadas con información de facciones
                            val enrichedArmies = armyRepository.getArmies().map { army ->
                                val flagUrl = factions
                                    .find { it.key == army.faction }
                                    ?.flags_url
                                    .orEmpty()

                                ArmyUi(
                                    name = army.name,
                                    factionId = army.faction.orEmpty(),
                                    flagUrl = flagUrl
                                )
                            }

                            _armyState.value = ArmiesState.Success(
                                armies = enrichedArmies,
                                gameVersion = gameVersion
                            )
                            Logger.d("Armies loaded successfully: ${enrichedArmies.size}")
                        }

                        is Result.Error -> {
                            Logger.e("Error loading factions", factionsResult.exception)
                            _armyState.value = ArmiesState.Error(
                                factionsResult.message ?: "Error al cargar facciones"
                            )
                        }

                        Result.Loading -> {
                            // Ya manejado arriba
                        }
                    }

                } catch (e: Exception) {
                    Logger.e("Error loading armies", e)
                    _armyState.value = ArmiesState.Error(
                        e.message ?: "Error desconocido al cargar armadas"
                    )
                }
            }
        }
    }
}