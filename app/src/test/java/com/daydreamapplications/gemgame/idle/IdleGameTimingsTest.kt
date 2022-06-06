package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.idle.timing.IdleGameTimings
import com.daydreamapplications.gemgame.idle.timing.TimingPersistence
import com.daydreamapplications.gemgame.idle.upgrades.Upgrade
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeType
import io.mockk.*
import org.junit.Test

class IdleGameTimingsTest {

    private fun subject(
        timingPersistence: TimingPersistence = mockk(),
    ): IdleGameTimings {
        return IdleGameTimings(
            timingPersistence = timingPersistence,
        )
    }

    private fun upgrade(
        id: Int = 0,
        cost: Long = 100,
        type: UpgradeType = UpgradeType.SWAP_SPEED,
        multiplier: Double = 0.5,
    ): Upgrade {
        return Upgrade(
            id = id,
            cost = cost,
            type = type,
            multiplier = multiplier,
        )
    }

    @Test
    fun `swap speed upgrade updates persistence`() {
        val timingPersistence: TimingPersistence = mockk()
        every { timingPersistence.swapDurationInMs } returns 100L
        every { timingPersistence.swapDurationInMs = 80L } just Runs

        val upgrade = upgrade(
            type = UpgradeType.SWAP_SPEED,
            multiplier = 0.8,
        )

        subject(
            timingPersistence = timingPersistence,
        ).applyUpgrade(upgrade)

        verify {
            timingPersistence.swapDurationInMs = 80L
        }
    }
}