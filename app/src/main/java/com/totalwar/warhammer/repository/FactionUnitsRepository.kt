package com.totalwar.warhammer.repository

import androidx.lifecycle.MutableLiveData
import com.totalwar.warhammer.FactionUnitsQuery
import com.totalwar.warhammer.datasources.FactionUnitsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactionUnitsRepository(
    private val factionUnitsDataSource: FactionUnitsDataSource
) {
    val factionUnits = MutableLiveData<List<FactionUnitsQuery.Unit?>>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun findUnitsByFaction(id: String, gameVersion: String) {
        coroutineScope.launch(Dispatchers.IO) {
            factionUnits.postValue(factionUnitsDataSource.getUnitsFaction(id, gameVersion))
        }
    }
}
