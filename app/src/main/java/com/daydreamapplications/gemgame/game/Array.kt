package com.daydreamapplications.gemgame.game

fun <T> Array<Array<T>>.toIterable(): Iterable<T> = toList().flatMap { it.toList() }

fun <T> Array<Array<T>>.setAllBy(init: (Int, Int) -> T) {
    forEachIndexed { xIndex, column ->
        column.forEachIndexed { yIndex, _ ->
            set(xIndex, yIndex, init(xIndex, yIndex))
        }
    }
}

fun <T> Array<Array<T>>.forEach(action: (x:Int, y: Int, value: T) -> Unit) {
    forEachIndexed { xIndex, column ->
        column.forEachIndexed { yIndex, _ ->
            action(xIndex, yIndex, get(xIndex, yIndex))
        }
    }
}

operator fun <T> Array<Array<T>>.get(xIndex: Int, yIndex: Int) = this[xIndex][yIndex]

operator fun <T> Array<Array<T>>.set(xIndex: Int, yIndex: Int, value: T) {
    this[xIndex][yIndex] = value
}

operator fun <T> Array<Array<T>>.get(coordinates: Coordinates) = this[coordinates.x, coordinates.y]

operator fun <T> Array<Array<T>>.set(coordinates: Coordinates, value: T) {
    this[coordinates.x, coordinates.y] = value
}