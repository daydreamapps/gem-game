package com.daydreamapplications.gemgame.game

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.Flow

interface Score {

    val current: MutableState<Int>

    fun change(by: Int)
}