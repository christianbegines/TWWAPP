package com.totalwar.warhammer.database.army

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ArmyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArmy(army: Army)

    @Query("SELECT * FROM armies WHERE id = :empId")
    fun findArmyById(empId: String): Army

    @Query("SELECT * FROM armies")
    fun getArmies(): List<Army>

    @Update
    suspend fun updateArmyDetails(army: Army)

    @Delete
    suspend fun deleteArmy(army: Army)
}
