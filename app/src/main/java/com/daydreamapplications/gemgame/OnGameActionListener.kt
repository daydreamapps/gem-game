package com.daydreamapplications.gemgame

import com.daydreamapplications.gemgame.Coordinates
import com.daydreamapplications.gemgame.Direction

interface OnGameActionListener {

    fun onSelectedAction(coordinates: Coordinates)
    fun onSwapAction(coordinates: Coordinates, direction: Direction)
}