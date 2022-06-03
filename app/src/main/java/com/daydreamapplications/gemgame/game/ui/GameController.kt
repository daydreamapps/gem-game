package com.daydreamapplications.gemgame.game.ui

import com.daydreamapplications.gemgame.game.Coordinates
import com.daydreamapplications.gemgame.game.GameGrid
import com.daydreamapplications.gemgame.game.GameTimings

class GameController(
    val gameGrid: GameGrid,
    val gemRadius: Int,
    private val gameTimings: GameTimings,
) {

    var selectedGem: Coordinates? = null

    val radii: Array<Array<Int>> = buildIntGrid(gemRadius)
    val verticalOffsets: Array<Array<Int>> = buildIntGrid(0)
    val horizontalOffsets: Array<Array<Int>> = buildIntGrid(0)

    private fun buildIntGrid(init: Int): Array<Array<Int>> {
        return Array(gameGrid.width) { Array(gameGrid.height) { init } }
    }
}