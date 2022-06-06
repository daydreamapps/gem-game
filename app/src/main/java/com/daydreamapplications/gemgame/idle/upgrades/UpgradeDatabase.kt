package com.daydreamapplications.gemgame.idle.upgrades

import androidx.room.*

@Database(entities = [UpgradeEntity::class], version = 1)
abstract class UpgradeDatabase : RoomDatabase() {

    abstract fun upgradeDao(): UpgradeDao
}

@Dao
interface UpgradeDao {

    @Query("SELECT * FROM UpgradeEntity WHERE purchased=0 ORDER BY id")
//    @Query("SELECT 10 FROM UpgradeEntity WHERE purchased=0 ORDER BY id")
    suspend fun getAll(): List<UpgradeEntity>

    @Query("UPDATE UpgradeEntity SET purchased=1 WHERE id=:upgradeId")
    suspend fun purchase(upgradeId: Int)
//    suspend fun purchase(upgradeId: Int, purchased: Boolean)

    // TODO: remove once database is initialised properly
    @Insert
    suspend fun insert(upgrade: UpgradeEntity)
}

@Entity
data class UpgradeEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "cost") val cost: Long,
    @ColumnInfo(name = "type") val type: UpgradeType,
    @ColumnInfo(name = "multiplier") val multiplier: Double,
    @ColumnInfo(name = "purchased") val purchased: Boolean,
)
