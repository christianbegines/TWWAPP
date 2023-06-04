package com.totalwar.warhammer.util

import com.totalwar.warhammer.fragment.Ability

private const val PATTERN_ICON = """img:(.*?)(?=]])"""
private const val PATTERN_TYPE = """\[\[/img]](.+)"""
private const val ATTR_DESCRIPTION = "\\[\\[img:.*?\\[\\[/img\\]\\]"
private const val CONSTANT = "Constant"
private const val SECONDS = "seconds"
private const val SELF = "Self "
private const val ENEMY = "Enemy "
private const val ALLY = "Ally "
private const val GROUND = "Ground "

fun String.getAbilityType(): String? =
    PATTERN_TYPE.toRegex().find(this)?.groupValues?.getOrNull(1)?.trim()

fun String.getAbilityIcon(): String? =
    PATTERN_ICON.toRegex().find(this)?.groupValues?.getOrNull(1)

fun Ability.getAbilityDuration(): String {
    return this.phases?.firstOrNull()?.contact_phase_ability?.duration?.let {
        if (it == -1.0) {
            CONSTANT
        } else {
            "$it $SECONDS"
        }
    } ?: run {
        this.unit_special_ability?.active_time.let {
            if (it == -1.0) {
                CONSTANT
            } else {
                "$it $SECONDS"
            }
        }
    }
}

fun removeImageTags(input: String): String =
    input.replace(Regex(ATTR_DESCRIPTION), "")

fun Ability.Unit_special_ability.getTarget(): String {
    var affectedTargets = ""
    if (this.affect_self as? Boolean == true) affectedTargets += SELF
    if (this.target_enemies as? Boolean == true) affectedTargets += ENEMY
    if (this.target_ground as? Boolean == true) affectedTargets += GROUND
    if (this.target_friends as? Boolean == true) affectedTargets += ALLY
    return affectedTargets
}
