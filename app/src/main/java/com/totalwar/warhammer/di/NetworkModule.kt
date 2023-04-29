package com.totalwar.warhammer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.datasources.FactionDataSource
import com.totalwar.warhammer.datasources.FactionUnitsDataSource
import com.totalwar.warhammer.datasources.GameVersionDataSource
import com.totalwar.warhammer.datasources.UnitsDataSource
import com.totalwar.warhammer.services.apollo.apolloClient
import com.totalwar.warhammer.settings.Settings
import com.totalwar.warhammer.settings.SettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object NetworkModule {

    @Provides
    @Singleton
    fun providesApolloClient(): ApolloClient {
        return apolloClient
    }

    @Provides
    @Singleton
    fun providesGameVersionDataSource(apollo: ApolloClient): GameVersionDataSource {
        return GameVersionDataSource(apollo)
    }

    @Provides
    @Singleton
    fun providesFactionDataSource(apollo: ApolloClient): FactionDataSource {
        return FactionDataSource(apollo)
    }

    @Provides
    @Singleton
    fun providesFactionUnitsDataSource(apollo: ApolloClient): FactionUnitsDataSource {
        return FactionUnitsDataSource(apollo)
    }

    @Provides
    @Singleton
    fun providesUnitsDataSource(apollo: ApolloClient): UnitsDataSource {
        return UnitsDataSource(apollo)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Settings> =
        MultiProcessDataStoreFactory.create(
            serializer = SettingsSerializer(),
            produceFile = {
                File("${context.cacheDir.path}/myapp.preferences_pb")
            }
        )
}
