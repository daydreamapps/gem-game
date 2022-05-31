package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.game.GameConfig
import com.daydreamapplications.gemgame.idle.upgrades.Upgrade
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeType
import javax.inject.Inject

class IdleGameConfig @Inject constructor(
): GameConfig {

    override val width: Int = 8
    override val height: Int = 6
    override var swapDurationMs: Long = 500L

    fun upgrade(upgrade: Upgrade) {
        when(upgrade.type) {
            UpgradeType.SWAP_SPEED -> {
                swapDurationMs = (swapDurationMs * upgrade.multiplier).toLong()
            }
        }
    }
}