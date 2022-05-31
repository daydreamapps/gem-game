package com.daydreamapplications.gemgame.idle.upgrades

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class UpgradesRepository @Inject constructor() {

    private val mutableUpgrades: MutableStateFlow<List<Upgrade>> = MutableStateFlow(stubUpgrades)
    val upgrades: Flow<List<Upgrade>>
        get() = mutableUpgrades

    fun removeUpgradeFromList(upgrade: Upgrade) {
        mutableUpgrades.value -= upgrade
    }

    companion object {

        private val stubUpgrades = listOf(
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 100, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 200, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 400, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 800, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 1600, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 3200, multiplier = 0.9),
        )
    }
}