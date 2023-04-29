package com.totalwar.warhammer.datasources

import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.GameVersionsQuery

class GameVersionDataSource(
    private val apolloClient: ApolloClient
) {
    suspend fun getGameVersion(): GameVersionsQuery.Version? =
        apolloClient.query(GameVersionsQuery())
            .execute().data?.versions?.firstOrNull()
}
