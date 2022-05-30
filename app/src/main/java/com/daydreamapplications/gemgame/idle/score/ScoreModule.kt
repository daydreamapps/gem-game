package com.daydreamapplications.gemgame.idle.score

import com.daydreamapplications.gemgame.game.Score
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ScoreModule {

    @Binds
    abstract fun provideScore(scoreRepository: ScoreRepository): Score
}