package com.totalwar.warhammer.viewmodels.ability

import androidx.datastore.core.DataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.AbilityRepository
import com.totalwar.warhammer.settings.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbilityViewModel @Inject constructor(
    private val abilityRepository: AbilityRepository,
    private val dataStore: DataStore<Settings>
) : ViewModel() {
    val state: MutableLiveData<AbilityState> = MutableLiveData(AbilityState.Idle)

    fun findAbility(id: String) {
        state.postValue(AbilityState.Loading)
        viewModelScope.launch {
            dataStore.data.collect { settings ->
                state.postValue(
                    abilityRepository.getAbility(
                        id,
                        settings.gameVersion
                    )?.let {
                        AbilityState.Success(
                            it,
                            settings.gameVersion
                        )
                    } ?: AbilityState.Error
                )
            }
        }
    }
}
