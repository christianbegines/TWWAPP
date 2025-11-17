package com.totalwar.warhammer.viewmodels.factionunits

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.domain.usecase.GetFactionUnitsUseCase
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.Result
import com.totalwar.warhammer.util.getUnitsByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing Faction Units screen state
 * Uses GetFactionUnitsUseCase to separate business logic
 */
@HiltViewModel
class FactionUnitsViewModel @Inject constructor(
    private val getFactionUnitsUseCase: GetFactionUnitsUseCase,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    private val _faction = MutableStateFlow<FactionUnitsState>(FactionUnitsState.Idle)
    val faction: StateFlow<FactionUnitsState> = _faction.asStateFlow()

    fun findUnitsByFaction(id: String) {
        viewModelScope.launch {
            _faction.value = FactionUnitsState.Loading

            try {
                val settings = dataStore.data.first()
                when (val result = getFactionUnitsUseCase(id, settings.gameVersion)) {
                    is Result.Success -> {
                        _faction.value = FactionUnitsState.Success(
                            result.data,
                            result.data.getUnitsByType(),
                            settings.gameVersion
                        )
                    }
                    is Result.Error -> {
                        _faction.value = FactionUnitsState.Error(
                            result.message ?: "Error al cargar unidades de la facción"
                        )
                    }
                    Result.Loading -> {
                        // Ya manejado arriba
                    }
                }
            } catch (e: Exception) {
                _faction.value = FactionUnitsState.Error(
                    e.message ?: "Error inesperado al cargar unidades"
                )
            }
        }
    }
}
