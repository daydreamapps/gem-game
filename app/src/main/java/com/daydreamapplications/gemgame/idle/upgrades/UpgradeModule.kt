package com.daydreamapplications.gemgame.idle.upgrades

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UpgradeModule {

    @Provides
    fun provideUpgradeDatabase(@ApplicationContext context: Context): UpgradeDatabase {
        return Room.databaseBuilder(
            context,
            UpgradeDatabase::class.java,
            "upgrade-database"
        ).build()
    }
}