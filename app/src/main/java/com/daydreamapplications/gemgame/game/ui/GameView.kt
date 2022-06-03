package com.daydreamapplications.gemgame.game.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.daydreamapplications.gemgame.R
import com.daydreamapplications.gemgame.game.*
import com.daydreamapplications.gemgame.idle.IdleController
import java.util.concurrent.ArrayBlockingQueue
import kotlin.math.max

@Composable
fun GameView(
    score: Score? = null,
) {
    AndroidView(factory = { context ->
        GameView(context).also {
            it.score = score
        }
    })
}


class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val immutableGameConfig: GameConfig = GameConfig.default,
    private val gameTimings: GameTimings = GameTimings.default,
) : View(context, attrs, defStyleAttr), IGameView, OnGameActionListener {

    private val rect = Rect(0, 0, 0, 0)

    var score: Score? = null

    private val gemGrid: GameGrid = GameGrid(immutableGameConfig.width, immutableGameConfig.height)

    private val gestureListener: GemViewGestureListener = GemViewGestureListener(
        immutableGameConfig.width,
        immutableGameConfig.height,
        this
    )

    private val queue = ArrayBlockingQueue<GameAction>(3, true)

    private var isRemoving = false
    private var isDropping = false

    private var isInitialised = false

    private var hideDuration: Long = 500L

    private var gridPaddingPercent: Float = 0.1F

    companion object {
        // TODO: move to GameController
        var squareWidthPixels: Int = 0
    }

    private var gemRadius: Int = 0


    var idleController: IdleController? = null
        set(value) {
            field = value
            value?.onGameActionListener = this@GameView
        }

    private var gameController: GameController = GameController(gemGrid, gemRadius, gameTimings)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GameView, 0, 0).apply {
            try {
                hideDuration = getInteger(R.styleable.GameView_hideDuration, 500).toLong()

                gridPaddingPercent = getFloat(R.styleable.GameView_gridPaddingPercent, 0.1F)
            } finally {
                recycle()
            }
        }

        val gestureDetector = GestureDetector(context, gestureListener)
        val listener: (v: View, event: MotionEvent) -> Boolean =
            { _, motionEvent -> gestureDetector.onTouchEvent(motionEvent) }
        setOnTouchListener(listener)
    }

    override fun onDetachedFromWindow() {
        idleController?.onGameActionListener = null
        idleController = null

        super.onDetachedFromWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val ratio = immutableGameConfig.width / immutableGameConfig.height.toFloat()

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
        gemGrid.reset()

        isInitialised = true
        invalidate()
    }

    override fun select(selection: Coordinates) {
        gameController.selectedGem = selection
        invalidate()
    }

    override fun deselect() {
        gameController.selectedGem = null
        invalidate()
    }

    override fun remove(removals: List<Coordinates>, gemRemovalArray: IntArray) {

        ValueAnimator.ofInt(gemRadius, 0).apply {
            duration = hideDuration

            addUpdateListener { valueAnimator ->
                removals.forEach {
                    gameController.radii[it] = valueAnimator.animatedValue as Int
                }
                invalidate()
            }

            addOnEndListener {
                isRemoving = false
                val dropHeights: Array<Array<Int>> = gemGrid.removeGems(removals)
                drop(dropHeights, gemRemovalArray)
            }

            isRemoving = true
            start()
        }
    }

    override fun drop(drops: Array<Array<Int>>, gemRemovalArray: IntArray) {

        gameController.verticalOffsets.setAllBy { x, y -> drops[x, y] * squareWidthPixels }
        gameController.radii.setAllBy { _, _ -> gemRadius }

        val startingOffsets = buildIntGrid { x, y ->
            drops[x, y] * squareWidthPixels
        }

        val dropSquares = drops.toIterable().maxOrNull() ?: 0
        val maxDrop = dropSquares * squareWidthPixels
        val dropDuration = dropSquares * gameTimings.dropDuration

        ValueAnimator.ofInt(0, maxDrop).apply {

            duration = dropDuration

            addUpdateListener { valueAnimator ->
                val progress = valueAnimator.animatedValue as Int

                gameController.verticalOffsets.setAllBy { x, y ->
                    max(0, startingOffsets[x, y] - progress)
                }
                invalidate()
            }

            addOnEndListener {
                isDropping = false
                handleQueuedActions()
            }

            isDropping = true
            start()
        }
    }

    override fun swap(swap: Pair<Coordinates, Coordinates>) {
        gameController.swap(
            swap = swap,
            onUpdate = { invalidate() },
            onEnd = { handleQueuedActions() },
        )
    }

    override fun onSelectedAction(coordinates: Coordinates) {
        queue.add(GameAction.Select(coordinates))
        handleQueuedActions()
    }

    override fun onSwapAction(coordinates: Coordinates, direction: Direction) {
        queue.add(GameAction.Swap(coordinates, direction))
        handleQueuedActions()
    }

    override fun onAction(action: GameAction) {
        when (action) {
            is GameAction.Select -> {
                performSelection(action.coordinates)
            }
            is GameAction.Swap -> {
                performSwap(action.coordinates, action.direction)
            }
        }
    }

    fun handleQueuedActions() {
        if (isDropping) return
        if (isRemoving) return

        val action = queue.poll()
        if (action != null) {
            onAction(action)
            handleQueuedActions()
        } else {
            hideMatchedGemsIfPresent()
        }
    }

    private var selectedCoordinates: Coordinates? = null

    private fun performSelection(coordinates: Coordinates) {
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

    private fun performSwap(coordinates: Coordinates, direction: Direction) {
        // TODO: prevent swap of actively dropping gems
        if (direction == Direction.NONE) return

        selectedCoordinates = null

        val endCoordinates = coordinates.offset(direction)

        gemGrid.swapGems(coordinates, endCoordinates)

        swap(coordinates to endCoordinates)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        squareWidthPixels = width / immutableGameConfig.width
        gemRadius = (squareWidthPixels * (1 - gridPaddingPercent) / 2).toInt()
        gestureListener.squareWidthPixels = squareWidthPixels

        gameController = GameController(gemGrid, gemRadius, gameTimings)

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

        gameController.selectedGem?.apply {
            renderSelector(canvas, x, y)
        }
    }

    private fun GemType.draw(canvas: Canvas, xIndex: Int, yIndex: Int) {
        val drawable = drawable(resources) ?: return

        updateRectBounds(
            xIndex = xIndex,
            yIndex = yIndex,
            radius = gameController.radii[xIndex, yIndex]
        )

        drawable.bounds = rect
        drawable.draw(canvas)
    }

    private fun renderSelector(canvas: Canvas, xIndex: Int, yIndex: Int) {
        val selectorDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_selector, null) ?: return

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
        val verticalOffset = gameController.verticalOffsets[xIndex, yIndex]
        val horizontalOffset = gameController.horizontalOffsets[xIndex, yIndex]

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

    private fun buildIntGrid(init: (Int, Int) -> Int = { _, _ -> 0 }): Array<Array<Int>> {
        return Array(immutableGameConfig.width) { x -> Array(immutableGameConfig.height) { y -> init(x, y) } }
    }

    private fun hideMatchedGemsIfPresent(gemRemovalArray: IntArray = IntArray(immutableGameConfig.width)) {

        gemGrid.getAllMatches().apply {
            if (isNotEmpty()) {
                val coordinates = flatten()

                coordinates.groupBy { it.x }.forEach { (xIndex, coordinates) ->
                    gemRemovalArray[xIndex] += coordinates.size
                }

                score?.change(by = sumOf { it.score })

                remove(coordinates, gemRemovalArray)
            }
            gemGrid.print()
        }
    }
}