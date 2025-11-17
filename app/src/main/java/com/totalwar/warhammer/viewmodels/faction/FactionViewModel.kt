package com.totalwar.warhammer.viewmodels.faction

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.domain.usecase.GetAllFactionsUseCase
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
 * ViewModel for managing Faction screen state
 * Uses GetAllFactionsUseCase to separate business logic
 */
@HiltViewModel
class FactionViewModel @Inject constructor(
    private val getAllFactionsUseCase: GetAllFactionsUseCase,
    private val dataStore: DataStore<Settings>
) : ViewModel() {

    private val _factionList = MutableStateFlow<FactionState>(FactionState.Idle)
    val factionList: StateFlow<FactionState> = _factionList.asStateFlow()

    fun findAllFactions() {
        val currentState = _factionList.value
        _factionList.value = FactionState.Loading(
            if (currentState is FactionState.Success) {
                currentState.factionList
            } else emptyList()
        )
        viewModelScope.launch {
            try {
                val settings = dataStore.data.first()
                when (val result = getAllFactionsUseCase(settings.gameVersion)) {
                    is Result.Success -> {
                        val sortedFactions = result.data.sortedBy { it.subculture?.name }
                        _factionList.value = FactionState.Success(sortedFactions, settings.gameVersion)
                    }
                    is Result.Error -> {
                        _factionList.value = FactionState.Error(
                            result.exception.message ?: "Error desconocido al cargar facciones"
                        )
                    }
                    Result.Loading -> {
                        // Estado ya manejado arriba
                    }
                }
            } catch (e: Exception) {
                _factionList.value = FactionState.Error(
                    e.message ?: "Error inesperado al cargar facciones"
                )
            }
        }
    }
}
