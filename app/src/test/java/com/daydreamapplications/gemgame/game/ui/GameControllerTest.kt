package com.daydreamapplications.gemgame.game.ui

import com.daydreamapplications.gemgame.assertEquals
import com.daydreamapplications.gemgame.game.GameGrid
import com.daydreamapplications.gemgame.game.height
import com.daydreamapplications.gemgame.game.width
import junit.framework.TestCase

class GameControllerTest : TestCase() {

    private fun subject(
        gameGrid: GameGrid = GameGrid(width = 3, height = 2),
        gemRadius: Int = 10,
    ): GameController {
        return GameController(
            gameGrid = gameGrid,
            gemRadius = gemRadius,
        )
    }

    fun `test radii grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).radii.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }

    fun `test verticalOffsets grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).verticalOffsets.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }

    fun `test horizontalOffsets grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).horizontalOffsets.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }
}