package com.totalwar.warhammer.di

import com.totalwar.warhammer.datasources.AbilityDataSource
import com.totalwar.warhammer.datasources.FactionDataSource
import com.totalwar.warhammer.datasources.FactionUnitsDataSource
import com.totalwar.warhammer.datasources.GameVersionDataSource
import com.totalwar.warhammer.datasources.UnitsDataSource
import com.totalwar.warhammer.repository.AbilityRepository
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.repository.FactionUnitsRepository
import com.totalwar.warhammer.repository.GameVersionRepository
import com.totalwar.warhammer.repository.UnitsRepository
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
    fun provideFactionRepository(factionDataSource: FactionDataSource): FactionRepository {
        return FactionRepository(factionDataSource)
    }

    @Singleton
    @Provides
    fun provideGameVersionRepository(gameVersionDataSource: GameVersionDataSource): GameVersionRepository {
        return GameVersionRepository(gameVersionDataSource)
    }

    @Singleton
    @Provides
    fun provideFactionUnitsRepository(factionUnitsDataSource: FactionUnitsDataSource): FactionUnitsRepository {
        return FactionUnitsRepository(factionUnitsDataSource)
    }

    @Singleton
    @Provides
    fun provideUnitsRepository(unitsDataSource: UnitsDataSource): UnitsRepository {
        return UnitsRepository(unitsDataSource)
    }

    @Singleton
    @Provides
    fun provideAbilityRepository(abilityDataSource: AbilityDataSource): AbilityRepository {
        return AbilityRepository(abilityDataSource)
    }
}
