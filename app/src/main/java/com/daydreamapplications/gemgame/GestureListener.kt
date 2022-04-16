package com.daydreamapplications.gemgame

import android.view.GestureDetector
import android.view.MotionEvent

interface GestureListener : GestureDetector.OnGestureListener {

    override fun onSingleTapUp(e: MotionEvent?): Boolean = false
    override fun onFling(
        start: MotionEvent?,
        end: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean = false

    override fun onDown(e: MotionEvent?) = true
    override fun onShowPress(p0: MotionEvent?) {}
    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float) = false
    override fun onLongPress(p0: MotionEvent?) {}
}