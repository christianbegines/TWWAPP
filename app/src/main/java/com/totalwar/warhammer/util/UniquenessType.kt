package com.totalwar.warhammer.util

private const val RARE = "wh_main_anc_group_rare"
private const val UNCOMMON = "wh_main_anc_group_uncommon"
private const val EPIC = "wh_main_anc_group_unique"
private const val COMMON = "wh_main_anc_group_common"

sealed class UniquenessType(open val type: String) {
    object Common : UniquenessType(COMMON)
    object Uncommon : UniquenessType(UNCOMMON)
    object Rare : UniquenessType(RARE)
    object Epic : UniquenessType(EPIC)
}
