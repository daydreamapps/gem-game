package com.daydreamapplications.gemgame.idle.score

import com.daydreamapplications.gemgame.assertEquals
import io.mockk.*
import junit.framework.TestCase

class ScoreRepositoryTest : TestCase() {

    private fun subject(
        scorePersistence: ScorePersistence = mockk(),
    ): ScoreRepository {
        return ScoreRepository(
            scorePersistence = scorePersistence,
        )
    }

    fun `test read score`() {
        val scorePersistence: ScorePersistence = mockk()
        every { scorePersistence.score } returns 20000L

        subject(
            scorePersistence = scorePersistence,
        ).current.value assertEquals 20000L
    }

    fun `test write score`() {
        val scorePersistence: ScorePersistence = mockk()
        every { scorePersistence.score } returns 0L
        every { scorePersistence.score = 5000L } just Runs

        subject(
            scorePersistence = scorePersistence,
        ).apply {
            change(by = 5000L)

            current.value assertEquals 5000L
        }

        verify {
            scorePersistence.score = 5000L
        }
    }
}