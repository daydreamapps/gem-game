package com.daydreamapplications.gemgame

import android.graphics.Point
import android.view.MotionEvent
import kotlin.math.abs
import kotlin.math.hypot

private const val swipeThreshold = 100
private const val swipeVelocityThreshold = 100

class GemViewGestureListener(
    private val widthSquares: Int,
    private val heightSquares: Int,
    private val onGameActionListener: OnGameActionListener
) : GestureListener {

    var squareWidthPixels: Int = 0

    override fun onSingleTapUp(e: MotionEvent?): Boolean {

        return if (e != null) {

            val point = Point(e.x.toInt(), e.y.toInt())

            if (containsPoint(point)) {

                onGameActionListener.onSelectedAction(coordinatesForXYPixels(point.x, point.y))
            }
            true
        } else false
    }

    override fun onFling(
        start: MotionEvent?,
        end: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        if (start != null && end != null) {

            val dY = end.y - start.y
            val dX = end.x - start.x

            if (isSwipeBeyondThreshold(dY, dX, velocityX, velocityY)) {

                val direction = getSwipeDirection(dY, dX)

                if (direction != Direction.NONE) {

                    val coordinates = coordinatesForXYPixels(start.x.toInt(), start.y.toInt())

                    onGameActionListener.onSwapAction(
                        coordinates = coordinates,
                        direction = direction
                    )
                }
            }
        }
        return true
    }

    private fun isSwipeBeyondThreshold(
        dY: Float,
        dX: Float,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return hypot(dX, dY) > swipeThreshold && hypot(
            velocityX,
            velocityY
        ) > swipeVelocityThreshold
    }

    private fun getSwipeDirection(dY: Float, dX: Float): Direction {
        return when {
            abs(dX) > abs(dY) * 1.5 -> dX.toXAxisDirection()
            abs(dY) > abs(dX) * 1.5 -> dY.toYAxisDirection()
            else -> Direction.NONE
        }
    }

    private fun Float.toXAxisDirection(): Direction =
        if (this >= 0) Direction.RIGHT else Direction.LEFT

    private fun Float.toYAxisDirection(): Direction =
        if (this >= 0) Direction.DOWN else Direction.UP

    private fun columnIndexFromPixels(xPixels: Int): Int {
        return if (containsXPixels(xPixels)) {
            xPixels / squareWidthPixels
        } else {
            (-1)
        }
    }

    private fun rowIndexFromPixels(yPixels: Int): Int {
        return if (containsYPixels(yPixels)) {
            yPixels / squareWidthPixels
        } else {
            (-1)
        }
    }

    private fun coordinatesForXYPixels(xPixels: Int, yPixels: Int) =
        Coordinates(columnIndexFromPixels(xPixels), rowIndexFromPixels(yPixels))

    // grid contains pixels

    private fun containsXPixels(xPixels: Int) =
        xPixels >= 0 && xPixels < widthSquares * squareWidthPixels

    private fun containsYPixels(yPixels: Int) =
        yPixels >= 0 && yPixels < heightSquares * squareWidthPixels

    private fun containsPoint(point: Point) = containsXPixels(point.x) && containsYPixels(point.y)
}