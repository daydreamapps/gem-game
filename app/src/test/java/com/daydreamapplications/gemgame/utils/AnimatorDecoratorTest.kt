package com.daydreamapplications.gemgame.utils

import android.animation.ValueAnimator
import io.mockk.*
import junit.framework.TestCase

class AnimatorDecoratorTest : TestCase() {

    private fun subject(
        valueAnimator: ValueAnimator = mockk(),
    ): Animator.AnimatorDecorator {
        return Animator.AnimatorDecorator(
            valueAnimator = valueAnimator,
        )
    }

    fun `test start delegates to ValueAnimator`() {
        val valueAnimator: ValueAnimator = mockk()
        every { valueAnimator.start() } just Runs

        subject(
            valueAnimator = valueAnimator,
        ).start()

        verify { valueAnimator.start() }
    }

    fun `test end delegates to ValueAnimator`() {
        val valueAnimator: ValueAnimator = mockk()
        every { valueAnimator.end() } just Runs

        subject(
            valueAnimator = valueAnimator,
        ).end()

        verify { valueAnimator.end() }
    }
}