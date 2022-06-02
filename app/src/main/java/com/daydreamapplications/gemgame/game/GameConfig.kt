package com.daydreamapplications.gemgame.game

interface GameConfig {

    val width: Int
    val height: Int
    val swapDurationMs: Long

    companion object {

        val default: GameConfig
            get() = object : GameConfig {
                override val width: Int = 8
                override val height: Int = 6
                override val swapDurationMs: Long = 500L
            }
    }
}