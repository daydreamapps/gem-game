package com.daydreamapplications.gemgame

import com.daydreamapplications.gemgame.game.Coordinates
import org.junit.Test

class NewTest {

    /*
    * animator.betweenInts(
            range = squareWidthPixels downTo 0,
            durationMs = gameTimings.swapDurationMs,
            onUpdate = { value ->
                offset(coordinates[0], value, axis)
                offset(coordinates[1], -value, axis)
                onUpdate()
            },
            onEnd = {
                isSwapping = false
                onEnd()
            },
        )
        * */

    sealed interface Animation {

        fun animate()

        data class Move(
            val motion: Pair<Coordinates, Offset(x, y)>,
        ) : Animation

        interface MultiMove : Animation {
            val moves: List<Move>
        }

        data class Swap(
            val first: Coordinates,
            val second: Coordinates,
        ) : Animation

    }

    @Test
    fun `create swap animation from swap grid`() {

    }
}