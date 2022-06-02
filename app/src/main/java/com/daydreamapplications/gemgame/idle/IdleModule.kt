package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.game.GameTimings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class IdleModule {

    @Binds
    abstract fun bindGameTimings(gameTimings: IdleGameTimings): GameTimings
}