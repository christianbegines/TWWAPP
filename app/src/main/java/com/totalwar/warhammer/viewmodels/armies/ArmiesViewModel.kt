package com.totalwar.warhammer.viewmodels.armies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.ArmyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArmiesViewModel @Inject constructor(
    private val armyRepository: ArmyRepository
) : ViewModel() {

    private val _armyList = MutableStateFlow<ArmiesState>(ArmiesState.Idle)
    val armyList: StateFlow<ArmiesState> = _armyList.asStateFlow()

    fun findAllArmies() {
        _armyList.value = ArmiesState.Loading
        viewModelScope.launch {
            _armyList.value = ArmiesState.Success
        }
    }
}