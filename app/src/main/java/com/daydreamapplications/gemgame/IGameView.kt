package com.daydreamapplications.gemgame

import com.daydreamapplications.gemgame.Coordinates

interface IGameView {

    fun initialise()

    fun select(selection: Coordinates)

    fun deselect()

    fun remove(removals: List<Coordinates>, gemRemovalArray: IntArray)

    fun drop(drops: Array<Array<Int>>, gemRemovalArray: IntArray)

    fun swap(swap: Pair<Coordinates, Coordinates>)
}