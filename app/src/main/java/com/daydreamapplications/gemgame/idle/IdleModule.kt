package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.game.GameConfig
import com.daydreamapplications.gemgame.game.GameTimings
import com.daydreamapplications.gemgame.utils.Animator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class IdleModule {

    @Binds
    abstract fun bindGameTimings(gameTimings: IdleGameTimings): GameTimings

    @Binds
    abstract fun bindGameConfig(gameConfig: IdleGameConfig): GameConfig

    companion object {

        @Provides
        fun provideAnimator(): Animator.Companion = Animator
    }
}