package com.daydreamapplications.gemgame.idle.upgrades

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpgradesRepository @Inject constructor() {

    val upgrades: Flow<List<Upgrade>>
        get() {
            return flow {
                emit(
                    listOf(
                        Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
                        Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
                        Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
                        Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
                        Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
                        Upgrade(type = UpgradeType.SWAP_SPEED, multiplier = 0.75),
                    )
                )
            }
        }
}