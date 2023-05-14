package com.totalwar.warhammer.viewmodels.factionunits

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.FactionUnitsRepository
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.isExclusive
import com.totalwar.warhammer.util.isHero
import com.totalwar.warhammer.util.isLord
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
                            faction,
                            faction.units?.filter { unit -> unit?.isLord() == true }.orEmpty(),
                            faction.units?.filter { unit -> unit?.isHero() == true && !unit.isExclusive() }
                                .orEmpty(),
                            faction.units?.filter { unit ->
                                (unit?.isHero() == false && !unit.isLord() && !unit.isExclusive())
                            },
                            faction.units?.filter{unit -> unit?.isExclusive() == true },
                            it.gameVersion
                        )
                    } ?: FactionUnitsState.Error
                )
            }
        }
    }
}
