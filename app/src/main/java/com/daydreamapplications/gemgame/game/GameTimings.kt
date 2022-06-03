package com.daydreamapplications.gemgame.game

interface GameTimings {

    val dropDuration: Long
    val hideDuration: Long
    val swapDurationMs: Long

    companion object {

        val default: GameTimings
            get() = object : GameTimings {

                override val dropDuration: Long = 100L
                override val hideDuration: Long = 500L
                override val swapDurationMs: Long = 500L
            }
    }
}