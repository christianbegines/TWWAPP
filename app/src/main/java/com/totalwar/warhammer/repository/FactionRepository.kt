package com.totalwar.warhammer.repository

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.FactionsQuery
import com.totalwar.warhammer.GameVersionsQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactionRepository(
    private val apolloClient: ApolloClient,
) {
    val allFactions = MutableLiveData<List<FactionsQuery.Faction?>>()
    val foundFaction = MutableLiveData<FactionsQuery.Faction?>()
    val gameVersion = MutableLiveData<GameVersionsQuery.Version?>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun getAllFactions(version: String) {
        coroutineScope.launch(Dispatchers.IO) {
            (apolloClient.query(FactionsQuery(version)).execute().data?.tww as FactionsQuery.Tww)
                .let {
                    it.factions?.let { factions -> allFactions.postValue(factions) }
                }
        }
    }

    fun getVersion() {
        coroutineScope.launch(Dispatchers.IO) {
            (
                apolloClient.query(GameVersionsQuery())
                    .execute().data?.versions as List<GameVersionsQuery.Version>
                ).let {
                it.firstOrNull().let { version ->
                    gameVersion.postValue(version)
                }
            }
        }
    }

    fun findFactionById(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
        }
    }
}
