package com.daydreamapplications.gemgame.game

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.daydreamapplications.gemgame.R
import kotlin.math.max

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr), IGameView, OnGameActionListener {

    private val gemGrid: GameGrid
    private var radii: Array<Array<Int>> = emptyArray()
    private var verticalOffsets: Array<Array<Int>> = emptyArray()
    private var horizontalOffsets: Array<Array<Int>> = emptyArray()
    private val rect = Rect(0, 0, 0, 0)

    private var isInitialised = false
    private var selectedGem: Coordinates? = null

    private var widthSquares: Int = 8
    private var heightSquares: Int = 5

    private var dropDuration: Long = 100L
    private var hideDuration: Long = 500L
    private var swapDuration = 150L

    private var gridPaddingPercent: Float = 0.1F

    private var widthRange: IntRange = 0..0
    private var heightRange: IntRange = 0..0

    private var squareWidthPixels: Int = 0

    private var gemRadius: Int = 0

    private val gestureListener: GemViewGestureListener

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GameView, 0, 0).apply {

            try {
                widthSquares = getInteger(R.styleable.GameView_widthSquares, 8)
                heightSquares = getInteger(R.styleable.GameView_heightSquares, 5)

                dropDuration = getInteger(R.styleable.GameView_dropDuration, 100).toLong()
                hideDuration = getInteger(R.styleable.GameView_hideDuration, 500).toLong()
                swapDuration = getInteger(R.styleable.GameView_swapDuration, 150).toLong()

                gridPaddingPercent = getFloat(R.styleable.GameView_gridPaddingPercent, 0.1F)
            } finally {
                recycle()
            }

            gemGrid = GameGrid(widthSquares, heightSquares)
        }

        gestureListener = GemViewGestureListener(widthSquares, heightSquares, this)
        val gestureDetector = GestureDetector(context, gestureListener)
        setOnTouchListener { _, motionEvent -> gestureDetector.onTouchEvent(motionEvent) }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val ratio = widthSquares / heightSquares.toFloat()

        var width = measuredWidth
        var height = measuredHeight
        val widthWithoutPadding = width - paddingLeft - paddingRight
        val heightWithoutPadding = height - paddingTop - paddingBottom

        val maxWidth = (heightWithoutPadding * ratio)
        val maxHeight = (widthWithoutPadding / ratio)

        if (widthWithoutPadding > maxWidth) {
            width = (maxWidth + paddingLeft + paddingRight).toInt()
        } else {
            height = (maxHeight + paddingTop + paddingBottom).toInt()
        }

        setMeasuredDimension(width, height)
    }

    override fun initialise() {
        // Remove matching groups from grid (clean start state)
        gemGrid.reset()

        verticalOffsets = buildIntGrid(0)
        horizontalOffsets = buildIntGrid(0)
        radii = buildIntGrid(gemRadius)

        isInitialised = true
        invalidate()
    }

    override fun select(selection: Coordinates) {
        selectedGem = selection
        invalidate()
    }

    override fun deselect() {
        selectedGem = null
        invalidate()
    }

    override fun remove(removals: List<Coordinates>, gemRemovalArray: IntArray) {

        radii = buildIntGrid(gemRadius)

        ValueAnimator.ofInt(gemRadius, 0).apply {
            duration = hideDuration

            addUpdateListener { valueAnimator ->
                removals.forEach {
                    radii[it] = valueAnimator.animatedValue as Int
                }
                invalidate()
            }

            addOnEndListener {
                val dropHeights = gemGrid.removeGems(removals)
                drop(dropHeights, gemRemovalArray)
            }

            start()
        }
    }

    override fun drop(drops: Array<Array<Int>>, gemRemovalArray: IntArray) {

        verticalOffsets.setAllBy { x, y -> drops[x, y] * squareWidthPixels }
        radii.setAllBy { _, _ -> gemRadius }

        val startingOffsets = buildIntGrid { x, y ->
            drops[x, y] * squareWidthPixels
        }

        val dropSquares = drops.toIterable().maxOrNull() ?: 0
        val maxDrop = dropSquares * squareWidthPixels
        val dropDuration = dropSquares * dropDuration

        ValueAnimator.ofInt(0, maxDrop).apply {

            duration = dropDuration

            addUpdateListener { valueAnimator ->
                val progress = valueAnimator.animatedValue as Int

                verticalOffsets.setAllBy { x, y ->
                    max(0, startingOffsets[x, y] - progress)
                }
                invalidate()
            }

            addOnEndListener {
                hideMatchedGemsIfPresent(gemRemovalArray)
            }

            start()
        }
    }

    override fun swap(swap: Pair<Coordinates, Coordinates>) {
        selectedGem = null

        val coordinates = swap.toList().sortedBy { it.x }.sortedBy { it.y }

        val axis = Coordinates.axis(swap)

        fun applyOffset(offset: Int) {
            when (axis) {
                Axis.HORIZONTAL -> {
                    horizontalOffsets[coordinates[0]] = offset
                    horizontalOffsets[coordinates[1]] = -offset
                }
                Axis.VERTICAL -> {
                    verticalOffsets[coordinates[0]] = offset
                    verticalOffsets[coordinates[1]] = -offset
                }
                else -> throw IllegalArgumentException("Coordinates must be adjacent top perform swap")
            }
        }

        ValueAnimator.ofInt(squareWidthPixels, 0).apply {

            duration = swapDuration

            addUpdateListener {
                applyOffset(it.animatedValue as Int)
                invalidate()
            }

            addOnEndListener { hideMatchedGemsIfPresent() }

            start()
        }
    }

    private var selectedCoordinates: Coordinates? = null

    override fun onSelectedAction(coordinates: Coordinates) {
        selectedCoordinates?.let { selected ->
            val inferredDirection = selected.getAdjacentDirection(coordinates)

            if (inferredDirection != Direction.NONE) {
                onSwapAction(selected, inferredDirection)
                return
            }
        }

        if (selectedCoordinates == coordinates) {
            selectedCoordinates = null
            deselect()
        } else {
            selectedCoordinates = coordinates
            select(coordinates)
        }
    }

    override fun onSwapAction(coordinates: Coordinates, direction: Direction) {
        if (direction == Direction.NONE) return

        selectedCoordinates = null

        val endCoordinates = coordinates.offset(direction)

        gemGrid.swapGems(coordinates, endCoordinates)

        swap(coordinates to endCoordinates)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        swapDuration = hideDuration / 3
        widthRange = 0 until widthSquares
        heightRange = 0 until heightSquares
        squareWidthPixels = width / widthSquares

        gemRadius = (squareWidthPixels * (1 - gridPaddingPercent) / 2).toInt()
        gestureListener.squareWidthPixels = squareWidthPixels

        if (!isInitialised) initialise()
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInitialised) return

        super.onDraw(canvas)

        renderGems(canvas = canvas)
    }

    private fun renderGems(
        canvas: Canvas,
    ) {
        gemGrid.forEachIndexed { xIndex, yIndex, gemType ->
            gemType.draw(canvas = canvas, xIndex = xIndex, yIndex = yIndex)
        }

        selectedGem?.apply {
            renderSelector(canvas, x, y)
        }
    }

    private fun GemType.draw(canvas: Canvas, xIndex: Int, yIndex: Int) {
        val drawable = drawable(resources) ?: return

        updateRectBounds(
            xIndex = xIndex,
            yIndex = yIndex,
            radius = radii[xIndex, yIndex]
        )

        drawable.bounds = rect
        drawable.draw(canvas)
    }

    private fun renderSelector(canvas: Canvas, xIndex: Int, yIndex: Int) {
        val selectorDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_selector, null) ?: return

        updateRectBounds(
            xIndex = xIndex,
            yIndex = yIndex,
            radius = squareWidthPixels / 2
        )

        selectorDrawable.bounds = rect
        selectorDrawable.draw(canvas)
    }

    private fun Rect.moveTo(
        xIndex: Int,
        yIndex: Int,
        radius: Int = gemRadius,
    ) {
        val verticalOffset = verticalOffsets[xIndex, yIndex]
        val horizontalOffset = horizontalOffsets[xIndex, yIndex]

        val x = ((xIndex + 0.5) * squareWidthPixels).toInt()
        val y = ((yIndex + 0.5) * squareWidthPixels).toInt()

        left = x + horizontalOffset - radius
        top = y + verticalOffset - radius
        right = x + horizontalOffset + radius
        bottom = y + verticalOffset + radius
    }

    private fun updateRectBounds(
        xIndex: Int,
        yIndex: Int,
        radius: Int = gemRadius,
    ) {
        rect.moveTo(xIndex, yIndex, radius)
    }

    // helper functions

    private fun buildIntGrid(init: Int): Array<Array<Int>> =
        Array(widthSquares) { Array(heightSquares) { init } }

    private fun buildIntGrid(init: (Int, Int) -> Int = { _, _ -> 0 }): Array<Array<Int>> =
        Array(widthSquares) { x -> Array(heightSquares) { y -> init(x, y) } }

    private fun hideMatchedGemsIfPresent(gemRemovalArray: IntArray = IntArray(widthSquares)) {

        gemGrid.getAllMatches().apply {
            if (isNotEmpty()) {
                val coordinates = flatten()

                coordinates.groupBy { it.x }.forEach { (xIndex, coordinates) ->
                    gemRemovalArray[xIndex] += coordinates.size
                }

                remove(coordinates, gemRemovalArray)
            }
            gemGrid.print()
        }
    }
}