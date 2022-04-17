package com.daydreamapplications.gemgame.v2

import com.daydreamapplications.gemgame.Coordinates

data class Group<T : Any>(
    val value: T,
    val coordinates: Set<Coordinates>,
) : Set<Coordinates> by coordinates

class GroupBuilder<T : Any>(
    private val grid: Grid<T>,
) {

    fun allGroups(): Set<Group<T>> {
        val groups: MutableSet<Group<T>> = mutableSetOf()

        grid.forEachIndexed { x, y, _ ->
            groups.add(groupStartingFrom(x, y))
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

        val linkedSquares: MutableSet<Coordinates> = mutableSetOf()


        var complete = false

        do {
            linkedSquares.clear()
            val connected = checkedSquares.map { linkedSquares(it) }
                .flatten()
                .toSet()
            linkedSquares.addAll(connected - checkedSquares)

            linkedSquares.forEach {
                if (grid.get(it) == value) {
                    groupedSquares.add(it)
                }
            }
            complete = checkedSquares == linkedSquares
            checkedSquares.addAll(linkedSquares)

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