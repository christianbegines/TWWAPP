package com.totalwar.warhammer.viewmodels.faction

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactionViewModel @Inject constructor(
    private val factionRepository: FactionRepository,
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
                dataStore.data.first().let { settings ->
                    val factions = factionRepository.getAllFactions(settings.gameVersion)
                        .sortedBy { it?.subculture?.name }
                    _factionList.value = FactionState.Success(factions, settings.gameVersion)
                }
            } catch (e: Exception) {
                _factionList.value = FactionState.Error
            }
        }
    }
}
