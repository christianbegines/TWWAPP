package com.totalwar.warhammer.viewmodels

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.GameVersionsQuery
import com.totalwar.warhammer.repository.FactionRepository
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
    val dataStore: DataStore<Settings>
) : ViewModel() {

    fun getGameVersion() {
        factionRepository.getVersion()
    }

    fun setGameVersionInSettings(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.updateData {
                Settings(id.ifEmpty { SETTINGS_DEFAULT_GAME_VERSION })
            }
        }
    }

    fun getAllFAction(id: String) {
        factionRepository.getAllFactions(
            id
        )
    }

    fun findUnitsByFaction(id: String, gameVersion: String) {
        factionRepository.findUnitsByFaction(id, gameVersion)
    }

    val gameVersion: MutableLiveData<GameVersionsQuery.Version?> = factionRepository.gameVersion
    val factionList: MutableLiveData<List<FactionsQuery.Faction?>> = factionRepository.allFactions
    val unitsFactionList: MutableLiveData<List<FactionUnitsQuery.Unit?>> =
        factionRepository.factionUnits
    val foundedFaction: MutableLiveData<FactionsQuery.Faction?> = factionRepository.foundFaction
}
