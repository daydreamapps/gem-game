package com.daydreamapplications.gemgame.idle.timing

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class TimingModule {

    companion object {

        @Provides
        @Named("Timing")
        fun provideScorePreferences(
            @ApplicationContext context: Context,
        ): SharedPreferences {
            return context.getSharedPreferences("Timing", Context.MODE_PRIVATE)
        }
    }
}