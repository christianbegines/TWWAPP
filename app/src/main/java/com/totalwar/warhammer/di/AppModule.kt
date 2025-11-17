package com.totalwar.warhammer.di

import com.totalwar.warhammer.datasources.AbilityDataSource
import com.totalwar.warhammer.datasources.ArmyDataSource
import com.totalwar.warhammer.datasources.FactionDataSource
import com.totalwar.warhammer.datasources.FactionUnitsDataSource
import com.totalwar.warhammer.datasources.GameVersionDataSource
import com.totalwar.warhammer.datasources.UnitsDataSource
import com.totalwar.warhammer.repository.AbilityRepository
import com.totalwar.warhammer.repository.ArmyRepository
import com.totalwar.warhammer.repository.FactionRepository
import com.totalwar.warhammer.repository.FactionUnitsRepository
import com.totalwar.warhammer.repository.GameVersionRepository
import com.totalwar.warhammer.repository.UnitsRepository
import com.totalwar.warhammer.repository.IAbilityRepository
import com.totalwar.warhammer.repository.IFactionRepository
import com.totalwar.warhammer.repository.IFactionUnitsRepository
import com.totalwar.warhammer.repository.IUnitsRepository
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import com.totalwar.warhammer.util.dispatcher.DefaultDispatcherProvider
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
    fun provideFactionRepository(factionDataSource: FactionDataSource): IFactionRepository {
        return FactionRepository(factionDataSource)
    }

    @Singleton
    @Provides
    fun provideGameVersionRepository(gameVersionDataSource: GameVersionDataSource): GameVersionRepository {
        return GameVersionRepository(gameVersionDataSource)
    }

    @Singleton
    @Provides
    fun provideFactionUnitsRepository(factionUnitsDataSource: FactionUnitsDataSource): IFactionUnitsRepository {
        return FactionUnitsRepository(factionUnitsDataSource)
    }

    @Singleton
    @Provides
    fun provideUnitsRepository(unitsDataSource: UnitsDataSource): IUnitsRepository {
        return UnitsRepository(unitsDataSource)
    }

    @Singleton
    @Provides
    fun provideAbilityRepository(abilityDataSource: AbilityDataSource): IAbilityRepository {
        return AbilityRepository(abilityDataSource)
    }

    @Singleton
    @Provides
    fun provideArmyRepository(armyDataSource: ArmyDataSource): ArmyRepository{
        return ArmyRepository(armyDataSource)
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
        return DefaultDispatcherProvider()
    }
}
