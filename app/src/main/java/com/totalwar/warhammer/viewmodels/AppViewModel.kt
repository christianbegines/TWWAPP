package com.totalwar.warhammer.viewmodels

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.GameVersionsQuery
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.repository.FactionUnitsRepository
import com.totalwar.warhammer.repository.GameVersionRepository
import com.totalwar.warhammer.repository.UnitsRepository
import com.totalwar.warhammer.settings.SETTINGS_DEFAULT_GAME_VERSION
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val factionRepository: FactionRepository,
    private val gameVersionRepository: GameVersionRepository,
    private val factionUnitsRepository: FactionUnitsRepository,
    private val unitsRepository: UnitsRepository,
    val dataStore: DataStore<Settings>
) : ViewModel() {

    val gameVersion: MutableLiveData<GameVersionsQuery.Version?> = gameVersionRepository.gameVersion
    val factionList: MutableLiveData<FactionListState> = MutableLiveData(FactionListState.Idle)
    val unitsFactionList: MutableLiveData<List<FactionUnitsQuery.Unit?>> =
        factionUnitsRepository.factionUnits
    val unit: MutableLiveData<UnitQuery.Unit?> = unitsRepository.unit

    fun getGameVersion() {
        gameVersionRepository.getVersion()
    }

    fun setGameVersionInSettings(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.updateData {
                Settings(id.ifEmpty { SETTINGS_DEFAULT_GAME_VERSION })
            }
        }
    }

    fun findAllFactions(gameVersion: String) {
        val currentState = factionList.value
        factionList.postValue(
            FactionListState.Loading(
                if (currentState is FactionListState.Success) {
                    currentState.factionList
                } else {
                    emptyList()
                }
            )
        )
        viewModelScope.launch {
            factionList.postValue(
                FactionListState.Success(
                    factionRepository.getAllFactions(
                        gameVersion
                    )
                )
            )
        }
    }

    fun findUnitsByFaction(id: String, gameVersion: String) {
        factionUnitsRepository.findUnitsByFaction(id, gameVersion)
    }

    fun findUnitById(id: String, gameVersion: String) {
        unitsRepository.getUnit(id, gameVersion)
    }
}

sealed class FactionListState {
    object Idle : FactionListState()
    data class Loading(
        val factionList: List<FactionsQuery.Faction?>
    ) : FactionListState()

    object Error : FactionListState()

    data class Success(
        val factionList: List<FactionsQuery.Faction?>
    ) : FactionListState()
}
