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

    fun `test initial state`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).apply {
            isRemoving assertEquals false

            radii.apply {
                width assertEquals 3
                height assertEquals 2
            }
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
            swap = Coordinates(x = 0, y = 0) to Coordinates(x = 1, y = 0),
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
            swap = Coordinates(x = 0, y = 0) to Coordinates(x = 1, y = 0),
            onUpdate = onUpdate,
            onEnd = { }
        )

        updateSlot.captured(100)

        verify {
            onUpdate()
        }
    }

    fun `test remove updates isRemoving state`() {
        val gameTimings = TestGameTimings.gameTimings(hideDuration = 100L)

        val animator: Animator.Companion = mockk()
        every {
            animator.between(
                range = 10..0,
                durationMs = 100L,
                onUpdate = any(),
                onEnd = any(),
            )
        } returns mockk()

        subject(
            animator = animator,
            gameTimings = gameTimings,
        ).apply {
            remove(
                removals = listOf(Coordinates(x = 0, y = 0)),
                onUpdate = {},
                onEnd = {},
            )

            isRemoving assertEquals true
        }

        verify {
            animator.between(
                range = 10..0,
                durationMs = 100L,
                onUpdate = any(),
                onEnd = any(),
            )
        }
    }

    fun `test remove onEnd isRemoving state`() {
        val animator: Animator.Companion = mockk()
        val endSlot: CapturingSlot<() -> Unit> = slot()

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
            animator = animator,
        ).apply {
            isRemoving assertEquals false

            remove(
                removals = listOf(Coordinates(x = 0, y = 0)),
                onUpdate = {},
                onEnd = onEnd,
            )
            isRemoving assertEquals true

            endSlot.captured()

            isRemoving assertEquals false
        }

        verify { onEnd() }
    }

    fun `test update sets radius`() {
        val animator: Animator.Companion = mockk()
        val updateSlot: CapturingSlot<(Int) -> Unit> = slot()
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
            animator = animator,
        ).apply {

            radii.get(0, 0) assertEquals 10

            remove(
                removals = listOf(Coordinates(x = 0, y = 0)),
                onUpdate = onUpdate,
                onEnd = {},
            )

            updateSlot.captured(5)
            radii.get(0, 0) assertEquals 5
        }

        verify { onUpdate() }
    }
}