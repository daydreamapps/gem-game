package com.daydreamapplications.gemgame.game

import com.daydreamapplications.gemgame.assertEquals
import junit.framework.TestCase

class IntGridKtTest : TestCase() {

    fun `test intGrid`() {
        intGrid(width = 3, height = 2, init = 1).apply {
            size assertEquals 3
            get(0).size assertEquals 2
            get(0)[0] assertEquals 1
        }
    }

    fun `test width`() {
        intGrid(width = 3, height = 2, init = 1).width assertEquals 3
    }

    fun `test height`() {
        intGrid(width = 3, height = 2, init = 1).height assertEquals 2
    }
}