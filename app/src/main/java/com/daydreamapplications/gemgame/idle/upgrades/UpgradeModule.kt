package com.daydreamapplications.gemgame.idle.upgrades

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking

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
            .prepopulateDatabaseIfRequired()
    }

    // TODO: create from file instead (requires SQL config file)
    private fun UpgradeDatabase.prepopulateDatabaseIfRequired(): UpgradeDatabase {
        runBlocking {
            val upgradeDao = upgradeDao()
            if (upgradeDao.getAll().isEmpty()) {
                stubUpgrades.forEach {
                    upgradeDao.insert(it)
                }
            }
        }
        return this
    }

    private companion object {

        val stubUpgrades = listOf(
            UpgradeEntity(id = 0, type = UpgradeType.DROP_SPEED, cost = 100, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 1, type = UpgradeType.REMOVAL_SPEED, cost = 200, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 2, type = UpgradeType.SWAP_SPEED, cost = 300, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 3, type = UpgradeType.DROP_SPEED, cost = 500, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 4, type = UpgradeType.REMOVAL_SPEED, cost = 800, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 5, type = UpgradeType.SWAP_SPEED, cost = 1300, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 6, type = UpgradeType.DROP_SPEED, cost = 2100, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 7, type = UpgradeType.REMOVAL_SPEED, cost = 3400, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 8, type = UpgradeType.SWAP_SPEED, cost = 5500, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 9, type = UpgradeType.DROP_SPEED, cost = 8900, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 10, type = UpgradeType.REMOVAL_SPEED, cost = 14400, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 11, type = UpgradeType.SWAP_SPEED, cost = 23300, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 12, type = UpgradeType.DROP_SPEED, cost = 37700, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 13, type = UpgradeType.REMOVAL_SPEED, cost = 61000, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 14, type = UpgradeType.SWAP_SPEED, cost = 98700, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 15, type = UpgradeType.DROP_SPEED, cost = 159700, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 16, type = UpgradeType.REMOVAL_SPEED, cost = 258400, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 17, type = UpgradeType.SWAP_SPEED, cost = 418100, multiplier = 0.9, purchased = false),
            UpgradeEntity(id = 18, type = UpgradeType.DROP_SPEED, cost = 676500, multiplier = 0.9, purchased = false),
        )
    }
}