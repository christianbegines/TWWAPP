package com.totalwar.warhammer.services.apollo

import com.apollographql.apollo3.ApolloClient

private const val URL = "https://broker.twwstats.com/graphql"
val apolloClient = ApolloClient.Builder()
    .serverUrl(URL)
    .build()
