package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.assertEquals
import com.daydreamapplications.gemgame.idle.upgrades.Upgrade
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeType
import junit.framework.TestCase

class IdleGameTimingsTest : TestCase() {

    private fun subject(): IdleGameTimings {
        return IdleGameTimings()
    }

    private fun upgrade(
        cost: Long = 100,
        type: UpgradeType = UpgradeType.SWAP_SPEED,
        multiplier: Double = 0.5,
    ): Upgrade {
        return Upgrade(
            cost = cost,
            type = type,
            multiplier = multiplier,
        )
    }

    fun `test initial values`() {
        subject().apply {
            dropDuration assertEquals 100L
            hideDuration assertEquals 500L
            swapDurationMs assertEquals 500L
        }
    }

    fun `test upgrade swap speed`() {

        val upgrade = upgrade(
            type = UpgradeType.SWAP_SPEED,
            multiplier = 0.8,
        )

        subject().apply {
            swapDurationMs assertEquals 500L

            applyUpgrade(upgrade)

            swapDurationMs assertEquals 400L
        }
    }
}