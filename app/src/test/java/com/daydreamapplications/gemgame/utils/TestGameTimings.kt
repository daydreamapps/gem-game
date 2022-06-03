package com.daydreamapplications.gemgame.utils

import com.daydreamapplications.gemgame.game.GameTimings

object TestGameTimings {

    const val swapDurationMs: Long = 500L
    const val dropDuration: Long = 100L

    fun gameTimings(
        swapDurationMs: Long = this.swapDurationMs,
        dropDuration: Long = this.dropDuration,
    ): GameTimings {
        return object : GameTimings {
            override val swapDurationMs: Long = swapDurationMs
            override val dropDuration: Long = dropDuration
        }
    }
}