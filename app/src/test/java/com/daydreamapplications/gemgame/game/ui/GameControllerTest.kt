package com.daydreamapplications.gemgame.game.ui

import com.daydreamapplications.gemgame.assertEquals
import com.daydreamapplications.gemgame.game.*
import com.daydreamapplications.gemgame.utils.Animator
import com.daydreamapplications.gemgame.utils.TestGameTimings
import io.mockk.*
import org.junit.Test

class GameControllerTest {

    private fun subject(
        gameGrid: GameGrid = GameGrid(width = 3, height = 2),
        gemRadius: Int = 10,
        gameTimings: GameTimings = GameTimings.default,
        animator: Animator.Companion = Animator,
        // TODO: move to GameMeasurements
        squareWidthPixels: Int = 10,
    ): GameController {
        return GameController(
            gameGrid = gameGrid,
            gemRadius = gemRadius,
            gameTimings = gameTimings,
            animator = animator,
        ).also {
            it.squareWidthPixels = squareWidthPixels
        }
    }

    @Test
    fun `initial state`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).apply {
            isDropping assertEquals false
            isRemoving assertEquals false
            isSwapping assertEquals false
            isAnimating assertEquals false

            radii.apply {
                width assertEquals 3
                height assertEquals 2
            }
        }
    }

    @Test
    fun `verticalOffsets grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).verticalOffsets.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }

    @Test
    fun `horizontalOffsets grid matches GameGrid`() {
        subject(
            gameGrid = GameGrid(width = 3, height = 2),
        ).horizontalOffsets.apply {
            width assertEquals 3
            height assertEquals 2
        }
    }

    @Test
    fun `swap updates isSwapping state`() {
        val animator: Animator.Companion = mockk()

        every {
            animator.betweenInts(
                range = 10 downTo 0,
                durationMs = 400L,
                onUpdate = any(),
                onEnd = any(),
            )
        } returns mockk()


        subject(
            gameTimings = TestGameTimings.gameTimings(swapDurationMs = 400L),
            animator = animator,
            squareWidthPixels = 10,
        ).apply {
            swap(
                swap = Coordinates(x = 0, y = 0) to Coordinates(1, 0),
                onUpdate = {},
                onEnd = {}
            )

            isSwapping assertEquals true
        }

        verify {
            animator.betweenInts(
                range = 10 downTo 0,
                durationMs = 400L,
                onUpdate = any(),
                onEnd = any(),
            )
        }
    }

    @Test
    fun `on swap end invokes onEnd callback`() {
        val animator: Animator.Companion = mockk()
        val endSlot = slot<() -> Unit>()

        every {
            animator.betweenInts(
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

    @Test
    fun `on swap update invokes onUpdate callback`() {
        val animator: Animator.Companion = mockk()
        val updateSlot = slot<(Int) -> Unit>()

        every {
            animator.betweenInts(
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

    @Test
    fun `remove updates isRemoving state`() {
        val gameTimings = TestGameTimings.gameTimings(hideDuration = 100L)

        val animator: Animator.Companion = mockk()
        every {
            animator.betweenInts(
                range = 10 downTo 0,
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
            animator.betweenInts(
                range = 10 downTo 0,
                durationMs = 100L,
                onUpdate = any(),
                onEnd = any(),
            )
        }
    }

    @Test
    fun `remove onEnd isRemoving state`() {
        val animator: Animator.Companion = mockk()
        val endSlot: CapturingSlot<() -> Unit> = slot()

        every {
            animator.betweenInts(
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

        verify {
            onEnd()
        }
    }

    @Test
    fun `update sets radius`() {
        val animator: Animator.Companion = mockk()
        val updateSlot: CapturingSlot<(Int) -> Unit> = slot()
        every {
            animator.betweenInts(
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

            radii[0, 0] assertEquals 10

            remove(
                removals = listOf(Coordinates(x = 0, y = 0)),
                onUpdate = onUpdate,
                onEnd = {},
            )

            updateSlot.captured(5)
            radii[0, 0] assertEquals 5
        }

        verify {
            onUpdate()
        }
    }

    @Test
    fun `drop updates isDropping state`() {
        val dropGrid = intGrid(width = 3, height = 2, init = 0).apply {
            set(xIndex = 0, yIndex = 0, value = 2)
        }

        val animator: Animator.Companion = mockk()
        every {
            animator.betweenInts(
                range = 0..(2 * 10), // squareWidthPixels is static & zero
                durationMs = 2 * GameTimings.default.dropDuration, // squareWidthPixels is static & zero
                onUpdate = any(),
                onEnd = any(),
            )
        } returns mockk()

        subject(
            gameGrid = GameGrid(width = 3, height = 2),
            gemRadius = 10,
            gameTimings = GameTimings.default,
            animator = animator,
            squareWidthPixels = 10,
        ).apply {
            isDropping assertEquals false

            drop(
                drops = dropGrid,
                onUpdate = {},
                onEnd = {},
            )

            isDropping assertEquals true
        }

        verify {
            animator.betweenInts(
                range = 0..(2 * 10),
                durationMs = 2 * GameTimings.default.dropDuration,
                onUpdate = any(),
                onEnd = any(),
            )
        }
    }

    @Test
    fun `drop onUpdate updates verticalOffsets`() {
        val dropGrid = intGrid(width = 3, height = 2, init = 0).apply {
            set(xIndex = 0, yIndex = 0, value = 2)
        }

        val animator: Animator.Companion = mockk()
        val updateSlot: CapturingSlot<(Int) -> Unit> = slot()
        every {
            animator.betweenInts(
                range = 0..(2 * 10), // squareWidthPixels is static & zero
                durationMs = 2 * GameTimings.default.dropDuration, // squareWidthPixels is static & zero
                onUpdate = capture(updateSlot),
                onEnd = any(),
            )
        } returns mockk()

        val onUpdate: () -> Unit = mockk(relaxed = true)

        subject(
            gameGrid = GameGrid(width = 3, height = 2),
            gemRadius = 10,
            gameTimings = GameTimings.default,
            animator = animator,
            squareWidthPixels = 10,
        ).apply {
            verticalOffsets[0, 0] assertEquals 0

            drop(
                drops = dropGrid,
                onUpdate = onUpdate,
                onEnd = {},
            )

            verticalOffsets[0, 0] assertEquals 20

            updateSlot.captured(5)

            verticalOffsets[0, 0] assertEquals 15
        }

        verify {
            onUpdate()
        }
    }

    @Test
    fun `drop onEnd updates isDropping state`() {
        val animator: Animator.Companion = mockk()
        val endSlot: CapturingSlot<() -> Unit> = slot()
        every {
            animator.betweenInts(
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
            isDropping assertEquals false

            drop(
                drops = intGrid(width = 3, height = 2, init = 0),
                onUpdate = {},
                onEnd = onEnd,
            )
            isDropping assertEquals true

            endSlot.captured()
            isDropping assertEquals false
        }

        verify {
            onEnd()
        }
    }
}