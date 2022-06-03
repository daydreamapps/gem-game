package com.daydreamapplications.gemgame.game.ui

import androidx.compose.animation.core.AnimationConstants
import com.daydreamapplications.gemgame.game.*
import com.daydreamapplications.gemgame.utils.Animator
import kotlin.math.max

class GameController(
    val gameGrid: GameGrid,
    val gemRadius: Int,
    private val gameTimings: GameTimings,
    private val animator: Animator.Companion = Animator.Companion,
) {

    var isDropping: Boolean = false
        private set
    var isRemoving: Boolean = false
        private set

    // TODO: move to GameMeasurements
    var squareWidthPixels: Int = 0

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

        animator.betweenInts(
            range = squareWidthPixels..0,
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

        animator.betweenInts(
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

    fun drop(
        drops: Array<Array<Int>>,
        onUpdate: () -> Unit,
        onEnd: () -> Unit,
    ) {

        verticalOffsets.setAllBy { x, y -> drops[x, y] * squareWidthPixels }
        radii.setAllBy { _, _ -> gemRadius }

        val startingOffsets = intGrid(gameGrid.width, gameGrid.height) { x, y ->
            drops[x, y] * squareWidthPixels
        }

        val dropSquares = drops.toIterable().maxOrNull() ?: 0
        val maxDrop = dropSquares * squareWidthPixels

        isDropping = true

        animator.betweenInts(
            range = 0..maxDrop,
            durationMs = dropSquares * gameTimings.dropDuration,
            onUpdate = { value ->
                verticalOffsets.setAllBy { x, y ->
                    max(0, startingOffsets[x, y] - value)
                }
                onUpdate()
            },
            onEnd = {
                isDropping = false
                onEnd()
            },
        )
    }
}