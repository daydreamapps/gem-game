package com.daydreamapplications.gemgame.game.ui

import androidx.compose.animation.core.AnimationConstants
import com.daydreamapplications.gemgame.game.*
import com.daydreamapplications.gemgame.utils.Animator

class GameController(
    val gameGrid: GameGrid,
    val gemRadius: Int,
    private val gameTimings: GameTimings,
    private val animator: Animator.Companion = Animator.Companion,
) {

    var isRemoving: Boolean = false
        private set


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

        animator.between(
            range = GameView.squareWidthPixels..0,
            durationMs = gameTimings.swapDurationMs,
            onUpdate = { value ->
                offset(coordinates[0], value, axis)
                offset(coordinates[1], -value, axis)
                onUpdate()
            },
            onEnd = onEnd,
        )
    }

    private fun offset(
        coordinates: Coordinates,
        offset: Int,
        axis: Axis,
    ) {
        AnimationConstants
        when (axis) {
            Axis.HORIZONTAL -> {
                horizontalOffsets[coordinates] = offset
            }
            Axis.VERTICAL -> {
                verticalOffsets[coordinates] = offset
            }
        }
    }

    fun remove(
        removals: List<Coordinates>,
        onUpdate: () -> Unit,
        onEnd: () -> Unit,
    ) {
        isRemoving = true

        animator.between(
            range = gemRadius..0,
            durationMs = gameTimings.hideDuration,
            onUpdate = { value ->
                removals.forEach {
                    radii[it] = value
                }
                onUpdate()
            },
            onEnd = {
                isRemoving = false
                onEnd()
            },
        )
    }
}