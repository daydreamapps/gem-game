package com.daydreamapplications.gemgame.idle

import android.animation.ValueAnimator
import androidx.compose.runtime.mutableStateOf
import com.daydreamapplications.gemgame.game.Coordinates
import com.daydreamapplications.gemgame.game.Direction
import com.daydreamapplications.gemgame.game.OnGameActionListener
import com.daydreamapplications.gemgame.game.addOnRepeatListener
import com.daydreamapplications.gemgame.idle.upgrades.Upgrade
import javax.inject.Inject
import kotlin.random.Random

class IdleController @Inject constructor(
    val gameConfig: IdleGameConfig,
    private val gameTimings: IdleGameTimings,
) {

    private val width: Int get() = gameConfig.width
    private val height: Int get() = gameConfig.height

    val swapDelayProgress = mutableStateOf(0f)

    private val swapAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        // TODO: handle changes to this value
        duration = 2000
        repeatCount = ValueAnimator.INFINITE

        addUpdateListener { swapDelayProgress.value = it.animatedFraction }
        addOnRepeatListener { move() }
    }

    var onGameActionListener: OnGameActionListener? = null

    fun resume() {
        swapAnimator.start()
    }

    fun pause() {
        swapAnimator.pause()
    }

    fun move() {
        onGameActionListener?.let {
            val coordinate = Coordinates(
                x = Random.nextInt(1, width - 1),
                y = Random.nextInt(1, height - 1),
            )

            it.onSwapAction(coordinate, Direction.random())
        }
    }

    fun applyUpgrade(upgrade: Upgrade) {
        gameTimings.applyUpgrade(upgrade)
    }
}