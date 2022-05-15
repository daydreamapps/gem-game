package com.daydreamapplications.gemgame.game

data class MatchedGroupArray(
    val gemType: GemType,
    val start: Point,
    val size: Int,
    val axis: Axis
) {

    val coordinates: Array<Coordinates> = Array(size) {
        Coordinates(
            start.x + if (axis == Axis.HORIZONTAL) it else 0,
            start.y + if (axis == Axis.VERTICAL) it else 0
        )
    }

    init {
        if (size < 3) throw IllegalArgumentException("Minimum number of matched squares is 3 (Actual = $size)")
    }
}