package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.game.GameTimings
import com.daydreamapplications.gemgame.idle.upgrades.Upgrade
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeType
import javax.inject.Inject

class IdleGameTimings @Inject constructor(
) : GameTimings {

    override var swapDurationMs: Long = 500L
    override var dropDuration: Long = 100L

    fun applyUpgrade(upgrade: Upgrade) {
        when (upgrade.type) {
            UpgradeType.SWAP_SPEED -> {
                swapDurationMs = (swapDurationMs * upgrade.multiplier).toLong()
            }
        }
    }
}