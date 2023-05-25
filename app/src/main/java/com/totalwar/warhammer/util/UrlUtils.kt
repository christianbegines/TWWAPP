package com.totalwar.warhammer.util

private const val UNIT_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/ui/units/icons/%s.png"
private const val LORD_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/"
private const val ICON_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/%s/ui/common ui/unit_category_icons/"
private const val ABILITY_ATTR_ICONS =
    "https://res.cloudinary.com/fishofstone/image/upload/w_64,h_64/twwstats/api/%s/ui/battle ui/ability_icons/"
private const val PORTHOLES = "portholes"
private const val UNITS = "units"

fun formatUrlUnitImage(gameVersion: String, param: String): String =
    String.format(UNIT_URL, gameVersion, param)

fun formatUrlHeroLordImage(param: String, gameVersion: String): String =
    "${String.format(LORD_URL, gameVersion)}${param.replace(PORTHOLES, UNITS)}"

fun formatUrlUnitIcon(param: String, gameVersion: String): String =
    "${String.format(ICON_URL, gameVersion)}$param.png"

fun formatUrlAbilityAttrIcon(param: String, gameVersion: String): String =
    "${String.format(ABILITY_ATTR_ICONS, gameVersion)}$param.png"
