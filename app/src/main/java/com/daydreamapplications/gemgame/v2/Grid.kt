package com.daydreamapplications.gemgame.v2

class Grid<T : Any>(
    val width: Int,
    val height: Int,
    val fillValue: (x: Int, y: Int) -> T,
) {

    private val columns: Array<Array<Any>> = Array(width) { xIndex ->
        Array(height) { yIndex ->
            fillValue(xIndex, yIndex)
        }
    }

    private val rows: Iterable<Iterable<Any>>
        get() {
            return (0 until height).map { yIndex ->
                columns.map { it[yIndex] }
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
                return if (rows.isNotEmpty()) {
                    buildFromRows()
                } else if (columns.isNotEmpty()) {
                    buildFromColumns()
                } else {
                    throw IllegalStateException("Cannot create grid without either rows or columns")
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
        return rows.joinToString(separator = "\n") { it.joinToString() }
    }
}

open class Pointer(
    val x: Int,
    val y: Int,
)