package com.totalwar.warhammer.di

import com.apollographql.apollo3.ApolloClient
import com.totalwar.warhammer.repository.FactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEmployeeRepository(apolloClient: ApolloClient): FactionRepository {
        return FactionRepository(apolloClient)
    }
}
