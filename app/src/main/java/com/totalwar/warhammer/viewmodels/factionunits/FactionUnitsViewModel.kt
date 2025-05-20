package com.totalwar.warhammer.viewmodels.factionunits

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.FactionUnitsRepository
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.getUnitsByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactionUnitsViewModel @Inject constructor(
    private val factionUnitsRepository: FactionUnitsRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    private val _faction = MutableStateFlow<FactionUnitsState>(FactionUnitsState.Idle)
    val faction: StateFlow<FactionUnitsState> = _faction.asStateFlow()

    fun findUnitsByFaction(id: String) {
        viewModelScope.launch {
            _faction.value = FactionUnitsState.Loading

            try {
                dataStore.data.first().let {
                    val factionUnits =
                        factionUnitsRepository.findUnitsByFaction(id, it.gameVersion)
                    if (factionUnits != null) {
                        _faction.value = FactionUnitsState.Success(
                            factionUnits,
                            factionUnits.getUnitsByType(),
                            it.gameVersion
                        )
                    } else {
                        _faction.value = FactionUnitsState.Error
                    }
                }

            } catch (e: Exception) {
                _faction.value = FactionUnitsState.Error
            }
        }
    }
}
