package com.totalwar.warhammer.viewmodels.ability

import com.totalwar.warhammer.fragment.Ability

sealed class AbilityState {
    object Idle : AbilityState()
    object Error : AbilityState()
    object Loading : AbilityState()
    data class Success(
        val ability: Ability,
        val gameVersion: String
    ) : AbilityState()
}
