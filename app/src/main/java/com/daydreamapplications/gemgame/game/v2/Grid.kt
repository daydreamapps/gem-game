package com.daydreamapplications.gemgame.game.v2

import com.daydreamapplications.gemgame.game.Coordinates
import com.daydreamapplications.gemgame.game.IGrid

@Suppress("UNCHECKED_CAST") // Required & well tested
open class Grid<T : Any>(
    override val width: Int,
    override val height: Int,
    val fillValue: (x: Int, y: Int) -> T,
) : IGrid<T> {

    private val columns: Array<Array<Any>> by lazy {
        Array(width) { xIndex ->
            Array(height) { yIndex ->
                fillValue(xIndex, yIndex)
            }
        }
    }

    private val rows: List<List<Any>>
        get() {
            return (0 until height).map { yIndex ->
                columns.map { it[yIndex] }
            }
        }

    override fun forEachIndexed(action: (x: Int, y: Int, T) -> Unit) {
        columns.forEachIndexed { xIndex, column ->
            column.forEachIndexed { yIndex, value ->
                action(xIndex, yIndex, value as T)
            }
        }
    }

    override fun columns(): List<List<T>> = columns.map { it.toList() as List<T> }

    override fun column(xIndex: Int): List<T> = columns[xIndex].toList() as List<T>

    override fun rows(): List<List<T>> = rows.map { it.toList() as List<T> }

    override fun row(yIndex: Int): List<T> = rows[yIndex].toList() as List<T>

    override fun get(xIndex: Int, yIndex: Int): T = columns[xIndex][yIndex] as T

    override fun set(xIndex: Int, yIndex: Int, value: T) {
        columns[xIndex][yIndex] = value
    }

    override fun get(coordinates: Coordinates): T {
        require(coordinates.x in 0 until width)
        require(coordinates.y in 0 until height)

        return coordinates.run {
            get(x, y)
        }
    }

    override fun set(coordinates: Coordinates, value: T) {
        coordinates.apply {
            set(x, y, value)
        }
    }

    override fun contains(coordinates: Coordinates): Boolean {
        return when {
            coordinates.x !in 0 until width -> false
            coordinates.y !in 0 until height -> false
            else -> true
        }
    }

    companion object {

        fun <T : Any> build(apply: Builder<T>.() -> Unit): Grid<T> {
            val builder = Builder<T>()
            apply(builder)
            return builder.build()
        }

        class Builder<T : Any> internal constructor() {
            private val rows: MutableList<List<T>> = ArrayList()
            private val columns: MutableList<List<T>> = ArrayList()

            fun row(vararg items: T) {
                if (columns.isNotEmpty()) {
                    throw IllegalArgumentException("Cannot create a Grid from a combination of rows and columns!")
                }
                when {
                    rows.isEmpty() -> {
                        rows.add(items.toList())
                    }
                    rows[0].size != items.size -> {
                        throw IllegalArgumentException("All rows must be of same size. Row containing ${items.size} items added to grid of width ${rows[0].size}")
                    }
                    else -> {
                        rows.add(items.toList())
                    }
                }
            }

            fun column(vararg items: T) {
                if (rows.isNotEmpty()) {
                    throw IllegalArgumentException("Cannot create a Grid from a combination of rows and columns!")
                }
                when {
                    columns.isEmpty() -> {
                        columns.add(items.toList())
                    }
                    columns[0].size != items.size -> {
                        throw IllegalArgumentException("All columns must be of same size. Row containing ${items.size} items added to grid of width ${columns[0].size}")
                    }
                    else -> {
                        columns.add(items.toList())
                    }
                }
            }

            fun build(): Grid<T> {
                return when {
                    rows.isNotEmpty() -> buildFromRows()
                    columns.isNotEmpty() -> buildFromColumns()
                    else -> throw IllegalStateException("Cannot create grid without either rows or columns")
                }
            }

            private fun buildFromRows(): Grid<T> {
                return Grid(
                    width = rows[0].size,
                    height = rows.size,
                    fillValue = { x, y -> rows[y][x] },
                )
            }

            private fun buildFromColumns(): Grid<T> {
                return Grid(
                    width = columns.size,
                    height = columns[0].size,
                    fillValue = { x, y -> columns[x][y] },
                )
            }
        }
    }

    override fun toString(): String {
        return rows.joinToString(separator = "\n") { it.joinToString(separator = ",") }
    }
}

data class Square<T : Any>(
    val x: Int,
    val y: Int,
    val value: T,
)