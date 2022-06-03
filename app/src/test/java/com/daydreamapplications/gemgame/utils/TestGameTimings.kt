package com.daydreamapplications.gemgame.utils

import com.daydreamapplications.gemgame.game.GameTimings

object TestGameTimings {

    const val dropDuration: Long = 100L
    const val hideDuration: Long = 500L
    const val swapDurationMs: Long = 500L

    fun gameTimings(
        dropDuration: Long = this.dropDuration,
        hideDuration: Long = this.hideDuration,
        swapDurationMs: Long = this.swapDurationMs,
    ): GameTimings {
        return object : GameTimings {
            override val dropDuration: Long = dropDuration
            override val hideDuration: Long = hideDuration
            override val swapDurationMs: Long = swapDurationMs
        }
    }
}