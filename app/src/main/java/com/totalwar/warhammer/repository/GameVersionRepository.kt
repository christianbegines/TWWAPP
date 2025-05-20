package com.totalwar.warhammer.repository

import com.totalwar.warhammer.GameVersionsQuery
import com.totalwar.warhammer.datasources.GameVersionDataSource

class GameVersionRepository(
    private val gameVersionDataSource: GameVersionDataSource
) {
    suspend fun getVersion(): GameVersionsQuery.Version? {
        return gameVersionDataSource.getGameVersion()
    }
}
