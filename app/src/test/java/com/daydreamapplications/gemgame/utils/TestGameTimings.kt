package com.daydreamapplications.gemgame.utils

import com.daydreamapplications.gemgame.game.GameTimings

object TestGameTimings {

    const val swapDurationMs: Long = 500L

    fun gameTimings(
        swapDurationMs: Long = this.swapDurationMs,
    ): GameTimings {
        return object : GameTimings {
            override val swapDurationMs: Long = swapDurationMs
        }
    }
}