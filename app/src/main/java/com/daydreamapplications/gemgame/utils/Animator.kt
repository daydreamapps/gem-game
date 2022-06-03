package com.daydreamapplications.gemgame.utils

import android.animation.ValueAnimator
import com.daydreamapplications.gemgame.game.addOnEndListener

interface Animator {

    fun start()
    fun end()

    data class AnimatorDecorator(
        private val valueAnimator: ValueAnimator,
    ) : Animator {
        override fun start() = valueAnimator.start()
        override fun end() = valueAnimator.end()
    }

    companion object {

        fun between(
            range: IntRange,
            durationMs: Long,
            onUpdate: (value: Int) -> Unit = {},
            onEnd: () -> Unit = {},
        ): Animator {
            val valueAnimator = ValueAnimator.ofInt(range.first, range.last).apply {

                duration = durationMs

                addUpdateListener { onUpdate(it.animatedValue as Int) }
                addOnEndListener(onEnd)

                start()
            }

            return AnimatorDecorator(valueAnimator)
        }
    }
}