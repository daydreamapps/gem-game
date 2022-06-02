package com.daydreamapplications.gemgame.game

interface GameTimings {
    val swapDurationMs: Long
}

interface GameConfig : GameTimings {

    val width: Int
    val height: Int

    companion object {

        val default: GameConfig
            get() = object : GameConfig {
                override val width: Int = 8
                override val height: Int = 6
                override val swapDurationMs: Long = 500L
            }
    }
}