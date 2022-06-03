package com.daydreamapplications.gemgame.game

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