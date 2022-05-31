package com.daydreamapplications.gemgame.game

interface GameConfig {

    val width: Int
    val height: Int
//    val dropDurationMs: Long
//    val hideDurationMs: Long
//    val swapDurationMs: Long

    companion object {

        val default: GameConfig
            get() = object : GameConfig {
                override val width: Int = 8
                override val height: Int = 6
//                override val dropDurationMs: Long = 100L
//                override val hideDurationMs: Long = 500L
//                override val swapDurationMs: Long = 150L
            }
    }
}