package com.totalwar.warhammer.viewmodels.armies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.totalwar.warhammer.repository.ArmyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArmiesViewModel @Inject constructor(
    private val armyRepository: ArmyRepository
) : ViewModel() {

    val armyList: MutableLiveData<ArmiesState> = MutableLiveData(ArmiesState.Idle)

    fun findAllArmies() {
        armyList.postValue(ArmiesState.Loading)
        viewModelScope.launch {
            armyList.postValue(
                ArmiesState.Success(
                    armyRepository.getArmies()
                )
            )
        }
    }
}