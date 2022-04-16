package com.daydreamapplications.gemgame

interface IGrid<T> {

    val width: Int
    val height: Int

    fun setAllBy(fillValue: (Int, Int) -> T)
    fun toIterable(): Iterable<T>
    fun forEachIndexed(action: (Int, Int, T) -> Unit)
    fun columns(): List<List<T>>
    fun column(xIndex: Int): List<T>
    fun rows(): List<List<T>>
    fun row(yIndex: Int): List<T>

    operator fun get(xIndex: Int, yIndex: Int): T
    operator fun set(xIndex: Int, yIndex: Int, value: T)
    operator fun get(coordinates: Coordinates): T
    operator fun set(coordinates: Coordinates, value: T)
}


@Suppress("UNCHECKED_CAST")
abstract class GenericGrid<T>(
    final override val width: Int,
    final override val height: Int,
    fillValue: (Int, Int) -> T
) : IGrid<T> {

    private val columns: List<MutableList<T>> = List(width) { xIndex ->
        MutableList(height) { yIndex ->
            fillValue(xIndex, yIndex)
        }
    }

    override fun setAllBy(fillValue: (Int, Int) -> T) {
        forEachIndexed { xIndex, yIndex, _ -> set(xIndex, yIndex, fillValue(xIndex, yIndex)) }
    }

    override fun toIterable(): Iterable<T> = columns.flatten()

    override fun forEachIndexed(action: (Int, Int, T) -> Unit) {
        columns.forEachIndexed { xIndex, column ->
            column.forEachIndexed { yIndex, value ->
                action(xIndex, yIndex, value)
            }
        }
    }

    override fun columns(): List<List<T>> = columns

    override fun column(xIndex: Int): List<T> = columns[xIndex]

    override fun rows(): List<List<T>> {
        return (0 until width).map { xIndex ->
            (0 until height).map { yIndex ->
                get(xIndex, yIndex)
            }
        }
    }

    override fun row(yIndex: Int): List<T> = rows()[yIndex]

    override fun get(xIndex: Int, yIndex: Int): T = columns[xIndex][yIndex]

    override fun set(xIndex: Int, yIndex: Int, value: T) {
        columns[xIndex][yIndex] = value
    }

    override fun get(coordinates: Coordinates): T = coordinates.run { get(x, y) }

    override fun set(coordinates: Coordinates, value: T) {
        coordinates.run { set(x, y, value) }
    }

    fun print(symbol: (T) -> Char) {
        println("Grid at ${System.currentTimeMillis()}")
        rows().forEachIndexed { column, items ->
            println("$column: ${items.map(symbol).joinToString()}")
        }
    }
}
