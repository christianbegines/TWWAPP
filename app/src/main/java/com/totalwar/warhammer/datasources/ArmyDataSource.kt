package com.totalwar.warhammer.datasources

import com.totalwar.warhammer.database.army.Army
import com.totalwar.warhammer.database.army.ArmyDao

class ArmyDataSource(
    private val armyDao: ArmyDao
) {

    suspend fun addArmy(newArmy: Army) {
        armyDao.addArmy(newArmy)
    }

    suspend fun updateArmy(army: Army) {
        armyDao.updateArmyDetails(army)
    }

    fun getAllArmies(): List<Army> {
        return armyDao.getArmies()
    }

    suspend fun deleteEmployee(army: Army) {
        armyDao.deleteArmy(army)
    }

    fun findEmployeeById(armyId: String) {
        armyDao.findArmyById(armyId)
    }
}