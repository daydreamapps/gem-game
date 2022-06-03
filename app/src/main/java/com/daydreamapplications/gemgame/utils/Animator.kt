package com.daydreamapplications.gemgame.utils

import android.animation.ValueAnimator
import com.daydreamapplications.gemgame.game.addOnEndListener

interface Animator {

    fun start()
    fun end()

    companion object {

        fun between(
            start: Int,
            end: Int,
            durationMs: Long,
            onUpdate: (value: Int) -> Unit,
            onEnd: () -> Unit,
        ) {
            ValueAnimator.ofInt(start, end).apply {

                duration = durationMs

                addUpdateListener { onUpdate(it.animatedValue as Int) }
                addOnEndListener(onEnd)

                start()
            }
        }
    }
}