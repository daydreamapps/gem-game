package com.daydreamapplications.gemgame.game

import kotlin.math.absoluteValue

data class Coordinates(val x: Int, val y: Int) {

    fun offset(direction: Direction, amount: Int = 1): Coordinates {
        return when (direction) {
            Direction.UP -> Coordinates(x, y - amount)
            Direction.DOWN -> Coordinates(x, y + amount)
            Direction.LEFT -> Coordinates(x - amount, y)
            Direction.RIGHT -> Coordinates(x + amount, y)
            else -> this
        }
    }

    fun getAdjacentDirection(to: Coordinates): Direction {
        if (x == to.x) {
            when (y - to.y) {
                1 -> return Direction.UP
                -1 -> return Direction.DOWN
            }
        } else if (y == to.y) {
            when (x - to.x) {
                1 -> return Direction.LEFT
                -1 -> return Direction.RIGHT
            }
        }

        return Direction.NONE
    }


    /**
     * Note: these coordinates are not bound by any grid validity
     */
    val touching: Set<Coordinates>
        get() = setOf(
            offset(Direction.UP, 1),
            offset(Direction.DOWN, 1),
            offset(Direction.LEFT, 1),
            offset(Direction.RIGHT, 1),
        )

    companion object {

        fun axis(coordinates: Pair<Coordinates, Coordinates>): Axis? {
            return axis(coordinates.first, coordinates.second)
        }

        private fun axis(first: Coordinates, second: Coordinates): Axis? {

            val dy = (first.y - second.y).absoluteValue
            val dx = (first.x - second.x).absoluteValue

            return when {
                dx == 1 && dy == 0 -> Axis.HORIZONTAL
                dx == 0 && dy == 1 -> Axis.VERTICAL
                else -> null
            }
        }
    }
}