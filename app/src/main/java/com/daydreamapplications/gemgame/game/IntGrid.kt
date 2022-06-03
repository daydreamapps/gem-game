package com.daydreamapplications.gemgame.game

typealias IntGrid = Array<Array<Int>>

fun intGrid(width: Int, height: Int, init: (x: Int, y: Int) -> Int): IntGrid {
    return Array(width) { x -> Array(height) { y -> init(x, y) } }
}

fun intGrid(width: Int, height: Int, init: Int): IntGrid {
    return intGrid(width, height) { _, _ -> init }
}

val IntGrid.width: Int
    get() = size

val IntGrid.height: Int
    get() {
        return if (width > 0) {
            val firstHeight = get(0).size
            require(all { it.size == firstHeight })
            firstHeight
        } else {
            0
        }
    }