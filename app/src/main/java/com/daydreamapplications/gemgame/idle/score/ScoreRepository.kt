package com.daydreamapplications.gemgame.idle.score

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.daydreamapplications.gemgame.game.Score
import javax.inject.Inject

class ScoreRepository @Inject constructor(
) : Score {

    // TODO: persistence
    override val current: MutableState<Long> = mutableStateOf(0)

    override fun change(by: Number) {
        current.value += by.toLong()
    }
}