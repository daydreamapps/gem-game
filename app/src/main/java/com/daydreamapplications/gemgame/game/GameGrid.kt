package com.daydreamapplications.gemgame.game

import android.util.Log
import com.daydreamapplications.gemgame.game.v2.Grid
import com.daydreamapplications.gemgame.game.v2.Group
import com.daydreamapplications.gemgame.game.v2.GroupBuilder

class GameGrid(
    width: Int,
    height: Int,
) : Grid<GemType>(width, height, { _, _ -> randomGemType() }) {


    fun getAllMatches(): List<Group<GemType>> {
        val start = System.currentTimeMillis()
        val toList = GroupBuilder(this).allGroups()
            .mapNotNull(Group<GemType>::match3)
            .toList()
        val end = System.currentTimeMillis()
        Log.v("Match Timing:", "${end - start}ms")
        return toList
    }

    fun swapGems(first: Coordinates, second: Coordinates) {
        val firstGem = get(first)
        val secondGem = get(second)

        set(first, secondGem)
        set(second, firstGem)
    }

    fun reset() {
        columns().forEachIndexed { xIndex, _ -> removeAllInColumn(xIndex) }

        val matches = getAllMatches()
        if (matches.isNotEmpty()) {
            Log.i("Grid init", "${matches.size} groups found")
            Log.i("Grid init", "\n $this")

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

    private fun removeAllInColumn(xIndex: Int) {
        column(xIndex).forEachIndexed { yIndex, _ ->
            set(xIndex, yIndex, randomGemType())
        }
        removeGemsInColumn(xIndex, 0 until width)
    }

    // ToDo: remove, call removeGemsInColumn directly
    private fun removeGems(xIndex: Int, indices: Iterable<Int>): Array<Int> = removeGemsInColumn(xIndex, indices)

    private fun removeGemsInColumn(xIndex: Int, indices: Iterable<Int>): Array<Int> {
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

    private fun getDropHeightsForColumn(xIndex: Int, indicesToRemove: Iterable<Int>): Array<Int> {
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

    fun print() {
        super.toString()
    }
}
