package com.daydreamapplications.gemgame.game

import kotlin.random.Random

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE,

    ;

    companion object {

        fun random(): Direction {
            val index = Random.nextInt(4)
            return values()[index]
        }
    }
}