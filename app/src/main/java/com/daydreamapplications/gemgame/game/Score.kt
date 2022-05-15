package com.daydreamapplications.gemgame.game

import kotlinx.coroutines.flow.Flow

interface Score {

    val current: Flow<Int>

    fun change(by: Int)
}