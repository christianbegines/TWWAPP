package com.totalwar.warhammer.viewmodels.faction

import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactionViewModel @Inject constructor(
    private val factionRepository: FactionRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    val factionList: MutableLiveData<FactionState> = MutableLiveData(FactionState.Idle)

    fun getGameVersion(callback: (String) -> Unit) {
        viewModelScope.launch {
            val version = dataStore.data
                .map { it.gameVersion }
                .first()
            callback(version)
        }
    }

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
                            it.sortedBy { faction -> faction?.subculture?.name },
                            settings.gameVersion
                        )
                    }

                )
            }
        }
    }
}
