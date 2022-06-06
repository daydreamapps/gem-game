package com.daydreamapplications.gemgame.idle.upgrades

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(entities = [UpgradeEntity::class], version = 1)
abstract class UpgradeDatabase : RoomDatabase() {

    abstract fun upgradeDao(): UpgradeDao
}

@Dao
interface UpgradeDao {

    // TODO: remove once database is initialised properly
    @Query("SELECT * FROM UpgradeEntity")
    suspend fun getAll(): List<UpgradeEntity>

    // TODO: remove once database is initialised properly
    @Insert
    suspend fun insert(upgrade: UpgradeEntity)

    @Query("SELECT * FROM UpgradeEntity WHERE purchased=0 ORDER BY id")
    fun availableUpgrades(): Flow<List<UpgradeEntity>>

    @Query("UPDATE UpgradeEntity SET purchased=1 WHERE id=:upgradeId")
    suspend fun purchase(upgradeId: Int)
}

@Entity
data class UpgradeEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "cost") val cost: Long,
    @ColumnInfo(name = "type") val type: UpgradeType,
    @ColumnInfo(name = "multiplier") val multiplier: Double,
    @ColumnInfo(name = "purchased") val purchased: Boolean,
) {

    val upgrade: Upgrade
        get() {
            return Upgrade(
                id = id,
                cost = cost,
                type = type,
                multiplier = multiplier,
            )
        }
}
