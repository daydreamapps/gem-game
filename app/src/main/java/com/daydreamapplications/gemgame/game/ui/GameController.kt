package com.daydreamapplications.gemgame.game.ui

import com.daydreamapplications.gemgame.game.GameGrid

class GameController(
    val gameGrid: GameGrid,
    val gemRadius: Int,
) {

    val radii: Array<Array<Int>> = buildIntGrid(gemRadius)
    val verticalOffsets: Array<Array<Int>> = buildIntGrid(0)
    val horizontalOffsets: Array<Array<Int>> = buildIntGrid(0)

    private fun buildIntGrid(init: Int): Array<Array<Int>> {
        return Array(gameGrid.width) { Array(gameGrid.height) { init } }
    }
}