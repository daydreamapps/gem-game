package com.daydreamapplications.gemgame.idle.score

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Named

class ScorePersistence @Inject constructor(
    @Named("Score")
    private val sharedPreferences: SharedPreferences,
) {

    // TODO: suspend/Room
    var score: Long
        get() = sharedPreferences.getLong(SCORE_KEY, 0L)
        set(value) {
            sharedPreferences.edit {
                putLong(SCORE_KEY, value)
            }
        }

    companion object {
        internal const val SCORE_KEY = "Score"
    }
}