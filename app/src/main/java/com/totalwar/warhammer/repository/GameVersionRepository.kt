package com.totalwar.warhammer.repository

import androidx.lifecycle.MutableLiveData
import com.totalwar.warhammer.GameVersionsQuery
import com.totalwar.warhammer.datasources.GameVersionDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameVersionRepository(
    private val gameVersionDataSource: GameVersionDataSource
) {
    val gameVersion = MutableLiveData<GameVersionsQuery.Version?>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getVersion() {
        coroutineScope.launch {
            gameVersion.postValue(gameVersionDataSource.getGameVersion())
        }
    }
}
