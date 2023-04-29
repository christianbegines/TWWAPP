package com.totalwar.warhammer.repository

import androidx.lifecycle.MutableLiveData
import com.totalwar.warhammer.UnitQuery
import com.totalwar.warhammer.datasources.UnitsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnitsRepository(
    private val unitsDataSource: UnitsDataSource
) {
    val unit = MutableLiveData<UnitQuery.Unit?>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun getUnit(id: String, gameVersion: String) {
        coroutineScope.launch(Dispatchers.IO) {
            unit.postValue(unitsDataSource.getUnitById(id, gameVersion))
        }
    }
}
