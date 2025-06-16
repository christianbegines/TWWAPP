package com.totalwar.warhammer.di

import android.content.Context
import com.totalwar.warhammer.database.WarHammerDataBase
import com.totalwar.warhammer.database.army.ArmyDao
import com.totalwar.warhammer.util.dispatcher.CoroutineDispatcherProvider
import com.totalwar.warhammer.util.dispatcher.DefaultDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideArmyDao(warHammerDataBase: WarHammerDataBase): ArmyDao {
        return warHammerDataBase.armyDao()
    }

    @Provides
    @Singleton
    fun provideWarHammerDataBase(@ApplicationContext context: Context): WarHammerDataBase {
        return WarHammerDataBase.getInstance(context)
    }

    @Provides
    fun provideDispatcherProvider(): CoroutineDispatcherProvider = DefaultDispatcherProvider()
}