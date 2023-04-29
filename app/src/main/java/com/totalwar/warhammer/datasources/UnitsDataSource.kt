package com.totalwar.warhammer.datasources

import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.UnitQuery

class UnitsDataSource(
    private val apolloClient: ApolloClient
) {
    suspend fun getUnitById(id: String, gameVersion: String): UnitQuery.Unit? =
        apolloClient.query(UnitQuery(gameVersion, id)).execute().data?.tww?.unit
}
