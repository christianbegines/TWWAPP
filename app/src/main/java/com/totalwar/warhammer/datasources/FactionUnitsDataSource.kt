package com.totalwar.warhammer.datasources

import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.FactionUnitsQuery

class FactionUnitsDataSource(
    private val apolloClient: ApolloClient
) {
    suspend fun getUnitsFaction(id: String, gameVersion: String): FactionUnitsQuery.Faction? =
        apolloClient.query(FactionUnitsQuery(gameVersion, id))
            .execute().data?.tww?.faction
}
