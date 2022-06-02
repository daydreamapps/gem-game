package com.daydreamapplications.gemgame.idle.score

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.daydreamapplications.gemgame.game.Score
import javax.inject.Inject

class ScoreRepository @Inject constructor(
    private val scorePersistence: ScorePersistence,
) : Score {

    override val current: MutableState<Long> = mutableStateOf(storedScore)

    override fun change(by: Number) {
        (storedScore + by.toLong()).also {
            storedScore = it
            current.value = it
        }
    }

    private var storedScore: Long
        get() = scorePersistence.score
        set(value) {
            scorePersistence.score = value
        }
}