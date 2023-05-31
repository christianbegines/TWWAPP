package com.totalwar.warhammer.datasources

import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.AbilityQuery
import com.totalwar.warhammer.fragment.Ability

class AbilityDataSource(
    private val apolloClient: ApolloClient
) {
    suspend fun getAbility(id: String, version: String): Ability? =
        apolloClient.query(AbilityQuery(version, id)).execute().data?.tww?.ability?.ability
}
