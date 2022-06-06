package com.daydreamapplications.gemgame.idle.upgrades

import com.daydreamapplications.gemgame.idle.upgrades.UpgradeDao.Companion.upgradeStream
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpgradesRepository @Inject constructor(
    private val upgradeDao: UpgradeDao,
) {

    private val mutableUpgrades: Flow<List<Upgrade>> by lazy { upgradeDao.upgradeStream() }
    val upgrades: Flow<List<Upgrade>>
        get() = mutableUpgrades

    suspend fun removeUpgradeFromList(upgrade: Upgrade) {
        upgradeDao.purchase(upgradeId = upgrade.id)
    }
}