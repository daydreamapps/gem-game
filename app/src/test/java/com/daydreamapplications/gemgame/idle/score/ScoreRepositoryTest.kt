package com.daydreamapplications.gemgame.idle.score

import android.content.SharedPreferences
import com.daydreamapplications.gemgame.assertEquals
import com.daydreamapplications.gemgame.utils.NullSharedPreferences
import io.mockk.*
import junit.framework.TestCase

class ScoreRepositoryTest : TestCase() {

    private fun subject(
        sharedPreferences: SharedPreferences = NullSharedPreferences(),
    ): ScoreRepository {
        return ScoreRepository(
            sharedPreferences = sharedPreferences,
        )
    }

    fun `test read score`() {
        val sharedPreferences: SharedPreferences = object : NullSharedPreferences() {
            override fun getLong(key: String?, defValue: Long): Long = 20000L
        }

        subject(
            sharedPreferences = sharedPreferences,
        ).current.value assertEquals 20000L
    }

    fun `test write score`() {
        val editor: SharedPreferences.Editor = mockk()
        every { editor.putLong("score", 5000L) } returns editor
        every { editor.apply() } just Runs

        val sharedPreferences: SharedPreferences = mockk()
        every { sharedPreferences.getLong("score", 0L) } returns 0L
        every { sharedPreferences.edit() } returns editor

        subject(
            sharedPreferences = sharedPreferences,
        ).apply {
            change(by = 5000L)

            current.value assertEquals 5000L
        }

        verifyOrder {
            editor.putLong("score", 5000L)
            editor.apply()
        }
    }
}