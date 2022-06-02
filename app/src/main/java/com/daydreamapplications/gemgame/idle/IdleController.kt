package com.daydreamapplications.gemgame.idle

import androidx.compose.runtime.mutableStateOf
import com.daydreamapplications.gemgame.game.Coordinates
import com.daydreamapplications.gemgame.game.Direction
import com.daydreamapplications.gemgame.game.OnGameActionListener
import kotlin.random.Random

class IdleController(
    var width: Int = 8,
    var height: Int = 5,
) {

    // TODO: swap duration

    val swapDelayProgress = mutableStateOf(0f)

    var onGameActionListener: OnGameActionListener? = null

    fun move() {
        onGameActionListener?.let {
            val coordinate = Coordinates(
                x = Random.nextInt(1, width - 1),
                y = Random.nextInt(1, height - 1),
            )

            it.onSwapAction(coordinate, Direction.random())
        }
    }
}