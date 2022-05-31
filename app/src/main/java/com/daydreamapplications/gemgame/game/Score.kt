package com.daydreamapplications.gemgame.game

import androidx.compose.runtime.MutableState

interface Score {

    val current: MutableState<Long>

    fun change(by: Number)
}