package com.daydreamapplications.gemgame.game

interface IGrid<T> {

    val width: Int
    val height: Int

    fun forEachIndexed(action: (Int, Int, T) -> Unit)
    fun columns(): List<List<T>>
    fun column(xIndex: Int): List<T>
    fun rows(): List<List<T>>
    fun row(yIndex: Int): List<T>

    operator fun get(xIndex: Int, yIndex: Int): T
    operator fun set(xIndex: Int, yIndex: Int, value: T)
    operator fun get(coordinates: Coordinates): T
    operator fun set(coordinates: Coordinates, value: T)
    fun contains(coordinates: Coordinates): Boolean
}