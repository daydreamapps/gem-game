package com.daydreamapplications.gemgame.game

interface GameTimings {

    val swapDurationMs: Long
    val dropDuration: Long

    companion object {

        val default: GameTimings
            get() = object : GameTimings {
                override val swapDurationMs: Long = 500L
                override val dropDuration: Long = 100L
            }
    }
}