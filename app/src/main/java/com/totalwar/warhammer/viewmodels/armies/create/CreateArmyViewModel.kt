package com.totalwar.warhammer.viewmodels.armies.create

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.ArmyRepository
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.viewmodels.faction.FactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateArmyViewModel @Inject constructor(
    private val factionRepository: FactionRepository,
    private val armyRepository: ArmyRepository,
    private val dataStore: DataStore<Settings>
): ViewModel() {
    val factionList: MutableLiveData<FactionState> = MutableLiveData(FactionState.Idle)

    fun findAllFactions() {
        val currentState = factionList.value
        factionList.postValue(
            FactionState.Loading(
                if (currentState is FactionState.Success) {
                    currentState.factionList
                } else {
                    emptyList()
                }
            )
        )
        viewModelScope.launch {
            dataStore.data.collect { settings ->
                factionList.postValue(
                    factionRepository.getAllFactions(
                        settings.gameVersion
                    ).let {
                        FactionState.Success(
                            it.sortedBy { faction -> faction?.subculture?.name }
                        )
                    }

                )
            }
        }
    }
}