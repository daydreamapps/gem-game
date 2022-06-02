package com.daydreamapplications.gemgame.game

sealed class GameAction {
    data class Select(val coordinates: Coordinates): GameAction()
    data class Swap(val coordinates: Coordinates, val direction: Direction): GameAction()
}

interface OnGameActionListener {

    fun onSelectedAction(coordinates: Coordinates)
    fun onSwapAction(coordinates: Coordinates, direction: Direction)

    fun onAction(action: GameAction)
}