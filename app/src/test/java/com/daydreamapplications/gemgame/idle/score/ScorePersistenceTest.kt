package com.daydreamapplications.gemgame.idle.score

import android.content.SharedPreferences
import com.daydreamapplications.gemgame.assertEquals
import com.daydreamapplications.gemgame.idle.score.ScorePersistence.Companion.SCORE_KEY
import com.daydreamapplications.gemgame.utils.NullSharedPreferences
import io.mockk.*
import junit.framework.TestCase

class ScorePersistenceTest : TestCase() {

    private fun subject(
        sharedPreferences: SharedPreferences = NullSharedPreferences(),
    ): ScorePersistence {
        return ScorePersistence(
            sharedPreferences = sharedPreferences,
        )
    }

    fun `test read score`() {
        val sharedPreferences: SharedPreferences = object : NullSharedPreferences() {
            override fun getLong(key: String?, defValue: Long): Long = 20000L
        }

        subject(
            sharedPreferences = sharedPreferences,
        ).score assertEquals 20000L
    }

    fun `test write score`() {
        val editor: SharedPreferences.Editor = mockk()
        every { editor.putLong(SCORE_KEY, 5000L) } returns editor
        every { editor.apply() } just Runs

        val sharedPreferences: SharedPreferences = mockk()
        every { sharedPreferences.edit() } returns editor

        subject(
            sharedPreferences = sharedPreferences,
        ).score = 5000L

        verifyOrder {
            editor.putLong(SCORE_KEY, 5000L)
            editor.apply()
        }
    }
}