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
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 300, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 500, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 800, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 1300, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 2100, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 3400, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 5500, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 8900, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 14400, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 23300, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 37700, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 61000, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 98700, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 159700, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 258400, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 418100, multiplier = 0.9),
            Upgrade(type = UpgradeType.SWAP_SPEED, cost = 676500, multiplier = 0.9),
        )
    }
}