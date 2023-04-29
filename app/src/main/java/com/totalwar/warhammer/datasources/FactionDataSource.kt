package com.totalwar.warhammer.datasources

import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.FactionsQuery

class FactionDataSource(
    private val apolloClient: ApolloClient
) {
    suspend fun getFactions(version: String): List<FactionsQuery.Faction?> =
        apolloClient.query(FactionsQuery(version)).execute().data?.tww?.factions.orEmpty()
}
