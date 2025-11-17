package com.totalwar.warhammer.viewmodels.armies.create

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.database.army.Army
import com.totalwar.warhammer.domain.usecase.GetAllFactionsUseCase
import com.totalwar.warhammer.repository.ArmyRepository
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.Logger
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.viewmodels.faction.FactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para crear nuevas armadas
 * Carga las facciones disponibles y permite crear una nueva armada
 */
@HiltViewModel
class CreateArmyViewModel @Inject constructor(
    private val getAllFactionsUseCase: GetAllFactionsUseCase,
    private val armyRepository: ArmyRepository,
    private val dataStore: DataStore<Settings>
): ViewModel() {

    private val _factionList = MutableStateFlow<FactionState>(FactionState.Idle)
    val factionList: StateFlow<FactionState> = _factionList.asStateFlow()

    fun findAllFactions() {
        val currentState = _factionList.value
        _factionList.value = FactionState.Loading(
            if (currentState is FactionState.Success) {
                currentState.factionList
            } else {
                emptyList()
            }
        )
        viewModelScope.launch {
            try {
                val settings = dataStore.data.first()
                when (val result = getAllFactionsUseCase(settings.gameVersion)) {
                    is Result.Success -> {
                        val sortedFactions = result.data.sortedBy { it.subculture?.name }
                        _factionList.value =
                            FactionState.Success(sortedFactions, settings.gameVersion)
                        Logger.d("Factions loaded successfully: ${sortedFactions.size}")
                    }

                    is Result.Error -> {
                        Logger.e("Error loading factions", result.exception)
                        _factionList.value = FactionState.Error(
                            result.message ?: "Error al cargar facciones"
                        )
                    }

                    Result.Loading -> {
                        // Ya manejado arriba
                    }
                }
            } catch (e: Exception) {
                Logger.e("Error in findAllFactions", e)
                _factionList.value = FactionState.Error(
                    e.message ?: "Error desconocido al cargar facciones"
                )
            }
        }
    }

    fun saveFaction(name: String, factionId: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                armyRepository.addArmy(
                    Army(
                        faction = factionId,
                        name = name
                    )
                )
                Logger.d("Army saved successfully: $name")
                onComplete()
            } catch (e: Exception) {
                Logger.e("Error saving army", e)
            }
        }
    }
}