package com.daydreamapplications.gemgame.idle.timing

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Named

class TimingPersistence @Inject constructor(
    @Named("Timing")
    private val sharedPreferences: SharedPreferences,
) {

    var dropDurationInMs: Long
        get() = sharedPreferences.getLong(DROP_KEY, 100L)
        set(value) {
            sharedPreferences.edit {
                putLong(DROP_KEY, value)
            }
        }

    var hideDurationInMs: Long
        get() = sharedPreferences.getLong(HIDE_KEY, 500L)
        set(value) {
            sharedPreferences.edit {
                putLong(HIDE_KEY, value)
            }
        }

    var swapDurationInMs: Long
        get() = sharedPreferences.getLong(SWAP_KEY, 500L)
        set(value) {
            sharedPreferences.edit {
                putLong(SWAP_KEY, value)
            }
        }


    companion object {
        internal const val DROP_KEY = "drop"
        internal const val HIDE_KEY = "hide"
        internal const val SWAP_KEY = "swap"
    }
}