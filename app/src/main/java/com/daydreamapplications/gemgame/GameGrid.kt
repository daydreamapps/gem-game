package com.daydreamapplications.gemgame

class GameGrid(
    width: Int,
    height: Int
) : GenericGrid<GemType>(width, height, { _, _ -> randomGemType() }) {


    fun getAllMatches(): List<MatchedGroupArray> {

        val horizontalMatches = rows()
            .mapIndexed { yIndex, _ -> getMatchesInColumn(yIndex) }
            .flatten()

        val verticalMatches = columns()
            .mapIndexed { xIndex, _ -> getMatchesInRow(xIndex) }
            .flatten()

        return horizontalMatches + verticalMatches
    }

    fun getMatchesInRow(yIndex: Int): List<MatchedGroupArray> = getMatches(row(yIndex), Axis.HORIZONTAL, yIndex)

    fun getMatchesInColumn(xIndex: Int): List<MatchedGroupArray> = getMatches(column(xIndex), Axis.VERTICAL, xIndex)

    private fun getMatches(range: Iterable<GemType>, axis: Axis, perpendicularIndex: Int): ArrayList<MatchedGroupArray> {
        val list = ArrayList<MatchedGroupArray>()
        val lastIndex = range.toList().lastIndex

        var type: GemType = range.first()
        var start = 0
        var size = 0

        range.forEachIndexed { index, gemType ->
            val gemsMatch = gemType == type
            if (gemsMatch && index != lastIndex) {
                size = index - start + 1
            } else {
                if (gemsMatch) size++
                if (size > 2) {
                    list.add(
                        if (axis == Axis.HORIZONTAL) {
                            MatchedGroupArray(type, Point(x = start, y = perpendicularIndex), size, axis)
                        } else {
                            MatchedGroupArray(type, Point(x = perpendicularIndex, y = start), size, axis)
                        }
                    )
                }

                type = range.toList()[index]
                start = index
                size = 1
            }
        }

        return list
    }

    fun swapGems(first: Coordinates, second: Coordinates) {
        val firstGem = get(first)
        val secondGem = get(second)

        set(first, secondGem)
        set(second, firstGem)
    }

    fun reset() {
        columns().forEachIndexed { xIndex, _ -> removeAllInColumn(xIndex) }

        if (getAllMatches().isNotEmpty()) {
            reset()
        }
    }

    fun removeGems(coordinates: List<Coordinates>): Array<Array<Int>> {
        val map: Map<Int, List<Coordinates>> = coordinates.distinct().groupBy { it.x }

        return columns().mapIndexed { index, _ ->
            val indices = (map[index] ?: emptyList()).map { it.y }
            removeGems(index, indices)
        }.toTypedArray()
    }

    fun removeAllInColumn(xIndex: Int) {
        column(xIndex).forEachIndexed { yIndex, _ ->
            set(xIndex, yIndex, randomGemType())
        }
        removeGemsInColumn(xIndex, 0 until width)
    }

    // ToDo: remove, call removeGemsInColumn directly
    fun removeGems(xIndex: Int, indices: Iterable<Int>): Array<Int> = removeGemsInColumn(xIndex, indices)

    fun removeGemsInColumn(xIndex: Int, indices: Iterable<Int>): Array<Int> {
        val column = column(xIndex).toList()
        val dropHeights = getDropHeightsForColumn(xIndex, indices)

        for (yIndex in column.indices) {
            val targetIndex = yIndex + dropHeights[yIndex]

            val newGemType = if (targetIndex < column.size) {
                column[yIndex + dropHeights[yIndex]]
            } else {
                randomGemType()
            }

            set(xIndex, yIndex, newGemType)
        }
        return dropHeights
    }

    fun getDropHeightsForColumn(xIndex: Int, indicesToRemove: Iterable<Int>): Array<Int> {
        val column = column(xIndex).toList()
        var count = 0
        val startLocation = Array(column.size + indicesToRemove.toList().size) {
            if (it < column.size && indicesToRemove.contains(it)) {
                -1
            } else {
                val currentCount = count
                count++
                currentCount
            }
        }
        return Array(column.size) { startLocation.indexOf(it) - it }
    }
}
