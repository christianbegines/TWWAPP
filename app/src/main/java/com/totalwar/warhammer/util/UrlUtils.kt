package com.totalwar.warhammer.util

private const val UNIT_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/ui/units/icons/%s.png"
private const val LORD_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/"
private const val ICON_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/%s/ui/common ui/unit_category_icons/"
private const val ABILITY_ATTR_ICONS =
    "https://res.cloudinary.com/fishofstone/image/upload/w_64,h_64/twwstats/api/%s/ui/battle ui/ability_icons/"
private const val ABILITY_ATTR_ICONS_EFFECT =
    "https://res.cloudinary.com/fishofstone/image/upload/w_64,h_64/twwstats/api/%s/ui/battle ui/ability_icons/%s.png"
private const val ABILITY_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/twwstats/api/%s/%s"
private const val MOUNT_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/%s"
private const val BATTLE_MOUNT_URL =
    "https://res.cloudinary.com/fishofstone/image/upload/q_100/twwstats/api/%s/%s"

private const val PORTHOLES = "portholes"
private const val UNITS = "units"
private const val PNG = ".png"

fun formatUrlUnitImage(gameVersion: String, param: String): String =
    String.format(UNIT_URL, gameVersion, param)

fun formatUrlMountImage(gameVersion: String, param: String): String =
    String.format(MOUNT_URL, gameVersion, param)
fun formatUrlBattleMountImage(gameVersion: String, param: String): String =
    String.format(BATTLE_MOUNT_URL, gameVersion, param)

fun formatUrlAbilityTypeImage(gameVersion: String, param: String): String =
    String.format(ABILITY_URL, gameVersion, param)
fun formatUrlAbilityTypeImageEffect(gameVersion: String, param: String): String =
    String.format(ABILITY_ATTR_ICONS_EFFECT, gameVersion, param)

fun formatUrlHeroLordImage(param: String, gameVersion: String): String =
    "${String.format(LORD_URL, gameVersion)}${param.replace(PORTHOLES, UNITS)}"

fun formatUrlUnitIcon(param: String, gameVersion: String): String =
    "${String.format(ICON_URL, gameVersion)}$param$PNG"

fun formatUrlAbilityAttrIcon(param: String, gameVersion: String): String =
    "${String.format(ABILITY_ATTR_ICONS, gameVersion)}$param$PNG"
