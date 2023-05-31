package com.totalwar.warhammer.util

import com.totalwar.warhammer.R

object TooltipUtils {
    fun getTittleResource(id: String?): Int? {
        return when (id) {
            UniquenessType.Common.type -> {
                R.drawable.tooltip_title_common
            }
            UniquenessType.Uncommon.type -> {
                R.drawable.tooltip_title_uncommon
            }
            UniquenessType.Rare.type -> {
                R.drawable.tooltip_title_rare
            }
            UniquenessType.Epic.type -> {
                R.drawable.tooltip_title_legendary
            }
            else -> null
        }
    }
}
