package com.github.maxastin.scorecounter.common.di

import com.github.maxastin.scorecounter.common.data.SharedPreferencesStorage
import com.github.maxastin.scorecounter.common.domain.KeyValueStorage
import com.github.maxastin.scorecounter.shared.data.GameRepositoryImpl
import com.github.maxastin.scorecounter.shared.domain.repo.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsKeyValueStorage(sharedPreferencesStorage: SharedPreferencesStorage): KeyValueStorage

    @Singleton
    @Binds
    fun bindsGameRepository(gameRepository: GameRepositoryImpl): GameRepository

}