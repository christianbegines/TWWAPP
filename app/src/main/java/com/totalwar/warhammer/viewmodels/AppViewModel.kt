package com.totalwar.warhammer.viewmodels

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.totalwar.warhammer.GameVersionsQuery
import com.totalwar.warhammer.repository.GameVersionRepository
import com.totalwar.warhammer.settings.SETTINGS_DEFAULT_GAME_VERSION
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val gameVersionRepository: GameVersionRepository,
    val dataStore: DataStore<Settings>
) : ViewModel() {

    val gameVersion: MutableLiveData<GameVersionsQuery.Version?> = gameVersionRepository.gameVersion
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
}
