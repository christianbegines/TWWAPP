package com.totalwar.warhammer.repository

import androidx.lifecycle.MutableLiveData
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.datasources.FactionDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactionRepository(
    private val factionDataSource: FactionDataSource
) {
    val factionList = MutableLiveData<List<FactionsQuery.Faction?>>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getAllFactions(version: String) {
        coroutineScope.launch(Dispatchers.IO) {
           factionList.postValue(factionDataSource.getFactions(version))
        }
    }
}
