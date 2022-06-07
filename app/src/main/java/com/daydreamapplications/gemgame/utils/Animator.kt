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
            range: IntProgression,
            durationMs: Long,
            onUpdate: (value: Int) -> Unit = {},
            onEnd: () -> Unit = {},
        ): Animator {
            ValueAnimator.ofObject()
            val valueAnimator = ValueAnimator.ofInt(range.first, range.last)


            valueAnimator.duration = durationMs
            valueAnimator.addUpdateListener { onUpdate(it.animatedValue as Int) }
            valueAnimator.addOnEndListener(onEnd)
            valueAnimator.start()

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