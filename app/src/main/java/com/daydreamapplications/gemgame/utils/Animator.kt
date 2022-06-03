package com.daydreamapplications.gemgame.utils

import android.animation.ValueAnimator
import com.daydreamapplications.gemgame.game.addOnEndListener
import com.daydreamapplications.gemgame.game.addOnRepeatListener

interface Animator {

    fun start()
    fun pause()
    fun end()

    data class AnimatorDecorator(
        private val valueAnimator: ValueAnimator,
    ) : Animator {
        override fun start() = valueAnimator.start()
        override fun pause() = valueAnimator.pause()
        override fun end() = valueAnimator.end()
    }

    companion object {

        fun betweenInts(
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

        fun loopingBetween(
            range: ClosedRange<Float>,
            durationMs: Long,
            onUpdate: (value: Float) -> Unit = {},
            onRepeat: () -> Unit = {},
        ): Animator {
            val valueAnimator = ValueAnimator.ofFloat(range.start, range.endInclusive).apply {

                duration = durationMs
                repeatCount = ValueAnimator.INFINITE

                addUpdateListener { onUpdate(it.animatedValue as Float) }
                addOnRepeatListener(onRepeat)

                start()
            }

            return AnimatorDecorator(valueAnimator)
        }
    }
}