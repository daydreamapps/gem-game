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
            Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
            Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
            Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
            Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
            Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
            Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
        )
    }
}