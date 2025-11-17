package com.totalwar.warhammer.viewmodels.ability

import com.totalwar.warhammer.fragment.Ability

/**
 * Represents different states of the Ability screen
 */
sealed class AbilityState {
    object Idle : AbilityState()
    data class Error(val message: String) : AbilityState()
    object Loading : AbilityState()
    data class Success(
        val ability: Ability,
        val gameVersion: String
    ) : AbilityState()
}
