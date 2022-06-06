package com.daydreamapplications.gemgame.idle.timing

import com.daydreamapplications.gemgame.game.GameTimings
import com.daydreamapplications.gemgame.idle.upgrades.Upgrade
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeType
import javax.inject.Inject

class IdleGameTimings @Inject constructor(
    private val timingPersistence: TimingPersistence,
) : GameTimings {

    override val dropDuration: Long
        get() = timingPersistence.dropDurationInMs

    override val hideDuration: Long
        get() = timingPersistence.hideDurationInMs

    override val swapDurationMs: Long
        get() = timingPersistence.swapDurationInMs

    fun applyUpgrade(upgrade: Upgrade) {
        when (upgrade.type) {
            UpgradeType.SWAP_SPEED -> {
                timingPersistence.swapDurationInMs = (swapDurationMs * upgrade.multiplier).toLong()
            }
        }
    }
}