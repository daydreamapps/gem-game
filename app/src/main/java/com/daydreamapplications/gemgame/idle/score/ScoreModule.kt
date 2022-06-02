package com.daydreamapplications.gemgame.idle.score

import android.content.Context
import android.content.SharedPreferences
import com.daydreamapplications.gemgame.game.Score
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class ScoreModule {

    @Binds
    abstract fun provideScore(scoreRepository: ScoreRepository): Score

    companion object {

        @Provides
        @Named("Score")
        fun provideScorePreferences(
            @ApplicationContext context: Context,
        ): SharedPreferences {
            return context.getSharedPreferences("Score", Context.MODE_PRIVATE)
        }
    }
}