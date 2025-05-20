package com.totalwar.warhammer.viewmodels.units

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.repository.UnitsRepository
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnitViewModel @Inject constructor(
    private val unitsRepository: UnitsRepository,
    private val factionRepository: FactionRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {

    private val _unitState = MutableStateFlow<UnitState>(UnitState.Idle)
    val unitState: StateFlow<UnitState> = _unitState.asStateFlow()

    fun findUnitById(id: String, factionId: String) {
        _unitState.value = UnitState.Loading
        viewModelScope.launch {
            dataStore.data.firstOrNull()?.let { settings ->
                val faction = factionRepository
                    .getAllFactions(settings.gameVersion)
                    .firstOrNull { it?.key == factionId }

                val unit = unitsRepository.getUnit(id, settings.gameVersion)

                if (unit != null && faction != null) {
                    _unitState.value = UnitState.Success(unit, faction, settings.gameVersion)
                } else {
                    _unitState.value = UnitState.Error
                }
            } ?: run {
                _unitState.value = UnitState.Error
            }
        }
    }
}
