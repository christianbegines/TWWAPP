package com.totalwar.warhammer.viewmodels.units

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.repository.UnitsRepository
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnitViewModel @Inject constructor(
    private val unitsRepository: UnitsRepository,
    private val factionRepository: FactionRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    val unit: MutableLiveData<UnitState> = MutableLiveData(UnitState.Idle)
    fun findUnitById(id: String, faction_id: String) {
        unit.postValue(UnitState.Loading)
        viewModelScope.launch {
            dataStore.data.collect { settings ->
                factionRepository.getAllFactions(settings.gameVersion)
                    .firstOrNull { it?.key == faction_id }?.let { faction ->
                        unitsRepository.getUnit(id, settings.gameVersion)?.let {
                            unit.postValue(UnitState.Success(it, faction, settings.gameVersion))
                        } ?: UnitState.Error
                    }
            }
        }
    }
}
