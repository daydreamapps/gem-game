package com.daydreamapplications.gemgame.game

interface OnGameActionListener {

    fun onSelectedAction(coordinates: Coordinates)
    fun onSwapAction(coordinates: Coordinates, direction: Direction)
}