package com.daydreamapplications.gemgame.game.ui

import com.daydreamapplications.gemgame.assertEquals
import com.daydreamapplications.gemgame.game.*
import com.daydreamapplications.gemgame.utils.Animator
import com.daydreamapplications.gemgame.utils.TestGameTimings
import io.mockk.*
import junit.framework.TestCase

class GameControllerTest : TestCase() {

    private fun subject(
        gameGrid: GameGrid = GameGrid(width = 3, height = 2),
        gemRadius: Int = 10,
        gameTimings: GameTimings = GameTimings.default,
        animator: Animator.Companion = Animator,
    ): GameController {
        return GameController(
            gameGrid = gameGrid,
            gemRadius = gemRadius,
            gameTimings = gameTimings,
            animator = animator,
        )
    }

    fun `test radii grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).radii.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }

    fun `test verticalOffsets grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).verticalOffsets.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }

    fun `test horizontalOffsets grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).horizontalOffsets.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }

    fun `test swap`() {
        val animator: Animator.Companion = mockk()

        every {
            animator.between(
                range = GameView.squareWidthPixels..0,
                durationMs = 400L,
                onUpdate = any(),
                onEnd = any(),
            )
        } returns mockk()


        subject(
            gameTimings = TestGameTimings.gameTimings(swapDurationMs = 400L),
            animator = animator,
        ).swap(
            swap = Coordinates(x = 0, y = 0) to Coordinates(1, 0),
            onUpdate = {},
            onEnd = {}
        )

        verify {
            animator.between(
                range = GameView.squareWidthPixels..0,
                durationMs = 400L,
                onUpdate = any(),
                onEnd = any(),
            )
        }
    }

    fun `test on swap end invokes onEnd callback`() {
        val animator: Animator.Companion = mockk()
        val endSlot = slot<() -> Unit>()

        every {
            animator.between(
                range = any(),
                durationMs = any(),
                onUpdate = any(),
                onEnd = capture(endSlot),
            )
        } returns mockk()

        val onEnd: () -> Unit = mockk(relaxed = true)

        subject(
            gameTimings = TestGameTimings.gameTimings(swapDurationMs = 400L),
            animator = animator,
        ).swap(
            swap = Coordinates(x = 0, y = 0) to Coordinates(1, 0),
            onUpdate = {},
            onEnd = onEnd
        )

        endSlot.captured()

        verify {
            onEnd()
        }
    }

    fun `test on swap update invokes onUpdate callback`() {
        val animator: Animator.Companion = mockk()
        val updateSlot = slot<(Int) -> Unit>()

        every {
            animator.between(
                range = any(),
                durationMs = any(),
                onUpdate = capture(updateSlot),
                onEnd = any(),
            )
        } returns mockk()

        val onUpdate: () -> Unit = mockk(relaxed = true)

        subject(
            gameTimings = TestGameTimings.gameTimings(swapDurationMs = 400L),
            animator = animator,
        ).swap(
            swap = Coordinates(x = 0, y = 0) to Coordinates(1, 0),
            onUpdate = onUpdate,
            onEnd = { }
        )

        updateSlot.captured(100)

        verify {
            onUpdate()
        }
    }
}