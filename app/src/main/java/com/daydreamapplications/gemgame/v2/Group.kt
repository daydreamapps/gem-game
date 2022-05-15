package com.daydreamapplications.gemgame.v2

import com.daydreamapplications.gemgame.Coordinates
import com.daydreamapplications.gemgame.Direction

data class Group<T : Any>(
    val value: T,
    val coordinates: Set<Coordinates>,
) : Set<Coordinates> by coordinates {

    val match3: Group<T>?
        get() {
            val horizontal = coordinates
                .filter(this::horizontalMatch)
                .toSet()

            val vertical = coordinates
                .filter(this::verticalMatch)
                .toSet()

            return Group(
                value = value,
                coordinates = horizontal + vertical
            ).takeIf { it.isNotEmpty() }
        }

    fun horizontalMatch(coordinate: Coordinates): Boolean {
        coordinate.run {
            val leftest = offset(Direction.LEFT, 2)
            val left = offset(Direction.LEFT, 1)
            val right = offset(Direction.RIGHT, 1)
            val rightest = offset(Direction.RIGHT, 2)

            return (coordinates.contains(leftest) && coordinates.contains(left)) ||
                    (coordinates.contains(left) && coordinates.contains(right)) ||
                    (coordinates.contains(right) && coordinates.contains(rightest))
        }
    }

    fun verticalMatch(coordinate: Coordinates): Boolean {
        coordinate.run {
            val upest = offset(Direction.UP, 2)
            val up = offset(Direction.UP, 1)
            val down = offset(Direction.DOWN, 1)
            val downest = offset(Direction.DOWN, 2)

            return (coordinates.contains(upest) && coordinates.contains(up)) ||
                    (coordinates.contains(up) && coordinates.contains(down)) ||
                    (coordinates.contains(down) && coordinates.contains(downest))
        }
    }
}

class GroupBuilder<T : Any>(
    private val grid: Grid<T>,
) {

    fun allGroups(): Set<Group<T>> {
        val groups: MutableSet<Group<T>> = mutableSetOf()

        grid.forEachIndexed { x, y, _ ->
            val coordinate = Coordinates(x, y)
            if (groups.none { it.contains(coordinate) }) {
                groups.add(groupStartingFrom(x, y))
            }
        }

        return groups
            .filter { it.size > 2 }
            .toSet()
    }

    fun groupStartingFrom(x: Int, y: Int): Group<T> {
        require(x in 0 until grid.width)
        require(y in 0 until grid.height)

        val value = grid[x, y]

        val checkedSquares: MutableSet<Coordinates> = mutableSetOf(Coordinates(x, y))
        val groupedSquares: MutableSet<Coordinates> = mutableSetOf(Coordinates(x, y))

        var complete: Boolean

        do {
            val connected = checkedSquares.map { linkedSquares(it) }
                .flatten()
                .toSet()
            val squaresToCheck = connected - checkedSquares

            squaresToCheck.forEach {
                if (grid.get(it) == value) {
                    groupedSquares.add(it)
                }
            }
            complete = squaresToCheck.isEmpty()
            checkedSquares.addAll(squaresToCheck)

        } while (!complete)

        return Group(
            value = value,
            coordinates = groupedSquares,
        )
    }

    fun linkedSquares(coordinates: Coordinates): Set<Coordinates> {
        return coordinates.touching
            .filter(grid::contains)
            .toSet()
    }
}