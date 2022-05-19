package com.daydreamapplications.gemgame.data

import android.content.Context
import androidx.room.Room
import com.daydreamapplications.gemgame.data.scores.ScoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideScoreDatabase(@ApplicationContext context: Context): ScoreDatabase {
        return Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "score-database"
        ).build()
    }
}