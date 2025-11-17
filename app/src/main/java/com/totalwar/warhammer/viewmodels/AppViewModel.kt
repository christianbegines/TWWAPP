package com.totalwar.warhammer.viewmodels

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.GameVersionsQuery
import com.totalwar.warhammer.repository.GameVersionRepository
import com.totalwar.warhammer.settings.SETTINGS_DEFAULT_GAME_VERSION
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.Logger
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Main ViewModel for application initialization
 * Handles game version retrieval and settings persistence
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    private val gameVersionRepository: GameVersionRepository,
    private val dataStore: DataStore<Settings>,
    private val dispatchers: CoroutineDispatcherProvider
) : ViewModel() {
    private val _gameVersion = MutableStateFlow<GameVersionsQuery.Version?>(null)
    val gameVersion: StateFlow<GameVersionsQuery.Version?> = _gameVersion

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getGameVersion() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Logger.d("Fetching game version...")
                val version = gameVersionRepository.getVersion()
                _gameVersion.value = version
                Logger.i("Game version loaded successfully: ${version?.id}")
            } catch (e: Exception) {
                Logger.e("Error fetching game version", e)
                // Mantener isLoading en true para que no muestre la UI sin datos
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setGameVersionInSettings(id: String) {
        viewModelScope.launch {
            try {
                withContext(dispatchers.io) {
                    dataStore.updateData {
                        Settings(id.ifEmpty { SETTINGS_DEFAULT_GAME_VERSION })
                    }
                }
                Logger.d("Game version saved to settings: $id")
            } catch (e: Exception) {
                Logger.e("Error saving game version to settings", e)
            }
        }
    }
}
