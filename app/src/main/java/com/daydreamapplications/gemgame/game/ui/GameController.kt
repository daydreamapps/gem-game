package com.daydreamapplications.gemgame.game.ui

import android.animation.ValueAnimator
import com.daydreamapplications.gemgame.game.*

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

    fun swap(
        swap: Pair<Coordinates, Coordinates>,
        onUpdate: () -> Unit,
        onEnd: () -> Unit,
    ) {
        selectedGem = null
        val coordinates = swap.toList().sortedBy { it.x }.sortedBy { it.y }
        val axis = Coordinates.axis(swap) ?: return

        // TODO: create unit-testable wrapper for ValueAnimator
        ValueAnimator.ofInt(GameView.squareWidthPixels, 0).apply {

            duration = gameTimings.swapDurationMs

            addUpdateListener {

                val offset = it.animatedValue as Int
                offset(coordinates[0], offset, axis)
                offset(coordinates[1], offset, axis)
                onUpdate()
            }

            addOnEndListener(onEnd)
        }.start()
    }

    fun offset(
        coordinates: Coordinates,
        offset: Int,
        axis: Axis,
    ) {
        when (axis) {
            Axis.HORIZONTAL -> {
                horizontalOffsets[coordinates] = offset
            }
            Axis.VERTICAL -> {
                verticalOffsets[coordinates] = offset
            }
        }
    }
}