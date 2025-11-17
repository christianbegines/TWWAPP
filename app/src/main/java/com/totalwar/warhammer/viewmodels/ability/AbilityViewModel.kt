package com.totalwar.warhammer.viewmodels.ability

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.domain.usecase.GetAbilityUseCase
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing Ability screen state
 * Uses GetAbilityUseCase to separate business logic
 */
@HiltViewModel
class AbilityViewModel @Inject constructor(
    private val getAbilityUseCase: GetAbilityUseCase,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    private val _state = MutableStateFlow<AbilityState>(AbilityState.Idle)
    val state: StateFlow<AbilityState> = _state.asStateFlow()

    fun findAbility(id: String) {
        _state.value = AbilityState.Loading
        viewModelScope.launch {
            try {
                val settings = dataStore.data.first()
                when (val result = getAbilityUseCase(id, settings.gameVersion)) {
                    is Result.Success -> {
                        _state.value = AbilityState.Success(result.data, settings.gameVersion)
                    }
                    is Result.Error -> {
                        _state.value = AbilityState.Error(
                            result.message ?: "Error al cargar la habilidad"
                        )
                    }
                    Result.Loading -> {
                        // Ya manejado arriba
                    }
                }
            } catch (e: Exception) {
                _state.value = AbilityState.Error(
                    e.message ?: "Error inesperado al cargar la habilidad"
                )
            }
        }
    }
}
