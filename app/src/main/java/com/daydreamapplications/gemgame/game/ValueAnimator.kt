package com.daydreamapplications.gemgame.game

import android.animation.Animator
import android.animation.ValueAnimator


fun ValueAnimator.addOnEndListener(onEnd: () -> Unit) {
    addListener(object : AnimatorListener {
        override fun onAnimationEnd(animation: Animator?) = onEnd()
    })
}

private interface AnimatorListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) = Unit
    override fun onAnimationEnd(animation: Animator?) = Unit
    override fun onAnimationCancel(animation: Animator?) = Unit
    override fun onAnimationStart(animation: Animator?) = Unit
}