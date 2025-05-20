package com.totalwar.warhammer.viewmodels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.GameVersionsQuery
import com.totalwar.warhammer.repository.GameVersionRepository
import com.totalwar.warhammer.settings.SETTINGS_DEFAULT_GAME_VERSION
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val gameVersionRepository: GameVersionRepository,
    val dataStore: DataStore<Settings>
) : ViewModel() {
    private val _gameVersion = MutableStateFlow<GameVersionsQuery.Version?>(null)
    val gameVersion: StateFlow<GameVersionsQuery.Version?> = _gameVersion

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getGameVersion() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val version = gameVersionRepository.getVersion() // Assume it returns the value
                _gameVersion.value = version
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setGameVersionInSettings(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.updateData {
                Settings(id.ifEmpty { SETTINGS_DEFAULT_GAME_VERSION })
            }
        }
    }
}
