package com.daydreamapplications.gemgame.idle

import androidx.compose.runtime.mutableStateOf
import com.daydreamapplications.gemgame.game.Coordinates
import com.daydreamapplications.gemgame.game.Direction
import com.daydreamapplications.gemgame.game.GameConfig
import com.daydreamapplications.gemgame.game.OnGameActionListener
import com.daydreamapplications.gemgame.idle.timing.IdleGameTimings
import com.daydreamapplications.gemgame.idle.upgrades.Upgrade
import com.daydreamapplications.gemgame.utils.Animator
import javax.inject.Inject
import kotlin.random.Random

class IdleController @Inject constructor(
    val gameConfig: GameConfig,
    private val gameTimings: IdleGameTimings,
    private val animator: Animator.Companion,
) {

    var onGameActionListener: OnGameActionListener? = null

    private val width: Int get() = gameConfig.width
    private val height: Int get() = gameConfig.height

    val swapDelayProgress = mutableStateOf(0f)

    private val idleActionAnimator: Animator by lazy {
        animator.loopingBetween(
            range = 0f..1f,
            durationMs = 2000L,
            onUpdate = {
                swapDelayProgress.value = it
            },
            onRepeat = {
                move()
            },
        )
    }

    fun resume() {
        idleActionAnimator.start()
    }

    fun pause() {
        idleActionAnimator.pause()
    }

    private fun move() {
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