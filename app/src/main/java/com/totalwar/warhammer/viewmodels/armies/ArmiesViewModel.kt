package com.totalwar.warhammer.viewmodels.armies

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.ArmyRepository
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArmiesViewModel @Inject constructor(
    private val armyRepository: ArmyRepository,
    private val factionRepository: FactionRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val dataStore: DataStore<Settings>
) : ViewModel() {

    private val _armyState = MutableStateFlow<ArmiesState>(ArmiesState.Idle)
    val armyState: StateFlow<ArmiesState> = _armyState.asStateFlow()

    fun findAllArmies() {
        viewModelScope.launch {
            _armyState.value = ArmiesState.Loading
            withContext(dispatcherProvider.io) {
                try {
                    val gameVersion = dataStore.data.first().gameVersion
                    val enrichedArmies = armyRepository.getArmies().map { army ->
                        val flagUrl = factionRepository.getAllFactions(gameVersion)
                            .find { it?.key == army.faction }?.flags_url.orEmpty()
                        ArmyUi(
                            name = army.name,
                            factionId = army.faction.orEmpty(),
                            flagUrl = flagUrl
                        )
                    }

                    _armyState.value = ArmiesState.Success(
                        armies = enrichedArmies,
                        gameVersion = gameVersion
                    )

                } catch (e: Exception) {
                    _armyState.value = ArmiesState.Error
                }
            }
        }
    }
}