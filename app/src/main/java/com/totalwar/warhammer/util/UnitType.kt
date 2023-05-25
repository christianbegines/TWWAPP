package com.totalwar.warhammer.util

private const val HERO_TYPE = "heroes_agents"
private const val INFANTRY_TYPE = "infantry"
private const val MONSTER_BEAST_TYPE = "monster_beasts"
private const val CAVALRY_CHARIOT_TYPE = "cavalry_chariots"
private const val LORD_TYPE = "commander"
private const val MISSILE_MONSTER_BEAST_TYPE = "missile_monster_beasts"
private const val MISSILE_CAVALRY_CHARIOT_TYPE = "missile_cavalry_chariots"
private const val MISSILE_INFANTRY_TYPE = "missile_infantry"
private const val ARTILLERY_WAR_MACHINES_TYPE = "artillery_war_machines "
private const val FLY_WAR_MACHINES_TYPE = "flying_war_machine"
private const val CONSTRUCTS_TYPE = "constructs"

sealed class UnitType(open val type: String) {
    object Lord : UnitType(LORD_TYPE)
    object Hero : UnitType(HERO_TYPE)
    object Infantry : UnitType(INFANTRY_TYPE)
    object MonsterBeast : UnitType(MONSTER_BEAST_TYPE)
    object CavalryChariot : UnitType(CAVALRY_CHARIOT_TYPE)
    object MissileMonsterBeast : UnitType(MISSILE_MONSTER_BEAST_TYPE)
    object MissileCavalryChariot : UnitType(MISSILE_CAVALRY_CHARIOT_TYPE)
    object MissileInfantry : UnitType(MISSILE_INFANTRY_TYPE)
    object ArtilleryWarMachine : UnitType(ARTILLERY_WAR_MACHINES_TYPE)
    object FlyWarMachine : UnitType(FLY_WAR_MACHINES_TYPE)
    object Construct : UnitType(CONSTRUCTS_TYPE)
}
