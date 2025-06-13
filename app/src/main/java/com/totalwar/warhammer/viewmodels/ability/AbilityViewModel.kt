package com.totalwar.warhammer.viewmodels.ability

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.AbilityRepository
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AbilityViewModel @Inject constructor(
    private val abilityRepository: AbilityRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    private val _state = MutableStateFlow<AbilityState>(AbilityState.Idle)
    val state: StateFlow<AbilityState> = _state.asStateFlow()

    fun findAbility(id: String) {
        _state.value = AbilityState.Loading
        viewModelScope.launch {
            dataStore.data
                .mapLatest { settings ->
                    val ability = abilityRepository.getAbility(id, settings.gameVersion)
                    ability?.let {
                        AbilityState.Success(it, settings.gameVersion)
                    } ?: AbilityState.Error
                }
                .catch { _state.value = AbilityState.Error }
                .collect { resultState ->
                    _state.value = resultState
                }
        }
    }
}
