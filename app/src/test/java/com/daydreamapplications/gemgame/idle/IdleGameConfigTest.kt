package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.assertEquals
import org.junit.Test

class IdleGameConfigTest {

    private fun subject(): IdleGameConfig = IdleGameConfig()

    @Test
    fun `sanity test`() {
        subject().apply {
            width assertEquals 8
            height assertEquals 6
        }
    }
}