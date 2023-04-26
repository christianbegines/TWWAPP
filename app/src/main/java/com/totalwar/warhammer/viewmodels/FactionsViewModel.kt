package com.totalwar.warhammer.viewmodels

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
class FactionsViewModel @Inject constructor(
    private val factionRepository: FactionRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {

    init {
        CoroutineScope(Dispatchers.Main).launch {
            dataStore.updateData {
                Settings(gameVersion.value?.id ?: SETTINGS_DEFAULT_GAME_VERSION)
            }
        }
    }

    fun getAllFAction() {
        factionRepository.getAllFactions()
    }

    fun findEmployeeById(id: Int) {
        factionRepository.findFactionById(id)
    }

    val gameVersion: MutableLiveData<GameVersionsQuery.Version?> = factionRepository.gameVersion
    val factionList: MutableLiveData<List<FactionsQuery.Faction?>> = factionRepository.allFactions
    val foundedFaction: MutableLiveData<FactionsQuery.Faction?> = factionRepository.foundFaction
}
