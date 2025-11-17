package com.totalwar.warhammer.viewmodels.units

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.domain.usecase.GetAllFactionsUseCase
import com.totalwar.warhammer.domain.usecase.GetUnitByIdUseCase
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing Unit screen state
 * Uses UseCases to separate business logic
 */
@HiltViewModel
class UnitViewModel @Inject constructor(
    private val getUnitByIdUseCase: GetUnitByIdUseCase,
    private val getAllFactionsUseCase: GetAllFactionsUseCase,
    private val dataStore: DataStore<Settings>
) : ViewModel() {

    private val _unitState = MutableStateFlow<UnitState>(UnitState.Idle)
    val unitState: StateFlow<UnitState> = _unitState.asStateFlow()

    fun findUnitById(id: String, factionId: String) {
        _unitState.value = UnitState.Loading
        viewModelScope.launch {
            try {
                val settings = dataStore.data.first()

                // Obtener facción y unidad en paralelo
                val factionResult = getAllFactionsUseCase(settings.gameVersion)
                val unitResult = getUnitByIdUseCase(id, settings.gameVersion)

                when {
                    unitResult is Result.Success && factionResult is Result.Success -> {
                        val faction = factionResult.data.firstOrNull { it.key == factionId }
                        if (faction != null) {
                            _unitState.value = UnitState.Success(
                                unitResult.data,
                                faction,
                                settings.gameVersion
                            )
                        } else {
                            _unitState.value = UnitState.Error(
                                "Facción no encontrada"
                            )
                        }
                    }
                    unitResult is Result.Error -> {
                        _unitState.value = UnitState.Error(
                            unitResult.message ?: "Error al cargar la unidad"
                        )
                    }
                    factionResult is Result.Error -> {
                        _unitState.value = UnitState.Error(
                            factionResult.message ?: "Error al cargar la facción"
                        )
                    }
                    else -> {
                        _unitState.value = UnitState.Error("Error inesperado")
                    }
                }
            } catch (e: Exception) {
                _unitState.value = UnitState.Error(
                    e.message ?: "Error inesperado al cargar la unidad"
                )
            }
        }
    }
}
