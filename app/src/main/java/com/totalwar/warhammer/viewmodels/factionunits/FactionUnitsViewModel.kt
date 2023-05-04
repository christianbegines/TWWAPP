package com.totalwar.warhammer.viewmodels.factionunits

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.FactionUnitsRepository
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactionUnitsViewModel @Inject constructor(
    private val factionUnitsRepository: FactionUnitsRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    val faction: MutableLiveData<FactionUnitsState> = MutableLiveData(FactionUnitsState.Idle)

    fun findUnitsByFaction(id: String) {
        faction.postValue(
            FactionUnitsState.Loading
        )
        viewModelScope.launch {
            dataStore.data.collect {
                faction.postValue(
                    factionUnitsRepository.findUnitsByFaction(id, it.gameVersion)?.let { faction ->
                        FactionUnitsState.Success(
                            faction
                        )
                    } ?: FactionUnitsState.Error
                )
            }
        }
    }

    fun getGameVersion(){
        viewModelScope.launch {
            return dataStore.data.collect {
                it.gameVersion
            }
        }
    }
}
