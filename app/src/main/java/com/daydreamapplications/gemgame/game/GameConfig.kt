package com.daydreamapplications.gemgame.game

interface GameTimings {
    val swapDurationMs: Long

    companion object {

        val default: GameTimings
            get() = object : GameTimings {
                override val swapDurationMs: Long = 500L
            }
    }
}

interface GameConfig {

    val width: Int
    val height: Int

    companion object {

        val default: GameConfig
            get() = object : GameConfig {
                override val width: Int = 8
                override val height: Int = 6
            }
    }
}