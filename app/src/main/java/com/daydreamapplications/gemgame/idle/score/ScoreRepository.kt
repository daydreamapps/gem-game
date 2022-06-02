package com.daydreamapplications.gemgame.idle.score

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import com.daydreamapplications.gemgame.game.Score
import javax.inject.Inject
import javax.inject.Named

class ScoreRepository @Inject constructor(
    // TODO: Wrapper class for Scores
    @Named("Score")
    private val sharedPreferences: SharedPreferences,
) : Score {

    override val current: MutableState<Long> = mutableStateOf(storedScore)

    override fun change(by: Number) {
        val newValue = storedScore + by.toLong()
        storedScore = newValue
        current.value = newValue
    }

    private var storedScore: Long
        get() = sharedPreferences.getLong("score", 0L)
        set(value) {
            sharedPreferences.edit {
                putLong("score", value)
            }
        }
}