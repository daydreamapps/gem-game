package com.daydreamapplications.gemgame.idle.upgrades

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UpgradesRepository @Inject constructor(
    private val upgradeDao: UpgradeDao,
) {

    private val mutableUpgrades: Flow<List<Upgrade>> by lazy {
        upgradeDao.availableUpgrades().map { it.map(UpgradeEntity::upgrade) }
    }
    val upgrades: Flow<List<Upgrade>>
        get() = mutableUpgrades

    // TODO: suspend
    fun removeUpgradeFromList(upgrade: Upgrade) {
        runBlocking {
            upgradeDao.purchase(upgradeId = upgrade.id)
        }
    }
}