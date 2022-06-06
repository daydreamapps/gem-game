package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.assertEquals
import com.daydreamapplications.gemgame.game.GameConfig
import com.daydreamapplications.gemgame.game.OnGameActionListener
import com.daydreamapplications.gemgame.idle.timing.IdleGameTimings
import com.daydreamapplications.gemgame.utils.Animator
import io.mockk.*
import org.junit.Test

class IdleControllerTest {

    private fun subject(
        gameConfig: GameConfig = GameConfig.default,
        gameTimings: IdleGameTimings = mockk(),
        animator: Animator.Companion = Animator,
    ): IdleController {
        return IdleController(
            gameConfig = gameConfig,
            gameTimings = gameTimings,
            animator = animator,
        )
    }

    @Test
    fun `resume starts animation`() {
        val animatorInstance: Animator = mockk()
        every { animatorInstance.start() } just Runs

        val animator: Animator.Companion = mockk()
        every {
            animator.loopingBetween(
                range = any(),
                durationMs = any(),
                onUpdate = any(),
                onRepeat = any(),
            )
        } returns animatorInstance

        subject(
            animator = animator,
        ).resume()

        verify { animatorInstance.start() }
    }

    @Test
    fun `pause pauses animation`() {
        val animatorInstance: Animator = mockk()
        every { animatorInstance.pause() } just Runs

        val animator: Animator.Companion = mockk()
        every {
            animator.loopingBetween(
                range = any(),
                durationMs = any(),
                onUpdate = any(),
                onRepeat = any(),
            )
        } returns animatorInstance

        subject(
            animator = animator,
        ).pause()

        verify { animatorInstance.pause() }
    }

    @Test
    fun `onUpdate updates progress`() {
        val animator: Animator.Companion = mockk()
        val updateSlot: CapturingSlot<(Float) -> Unit> = slot()
        every {
            animator.loopingBetween(
                range = any(),
                durationMs = any(),
                onUpdate = capture(updateSlot),
                onRepeat = any(),
            )
        } returns mockk(relaxed = true)

        subject(
            animator = animator,
        ).apply {
            swapDelayProgress.value assertEquals 0

            resume()
            updateSlot.captured(0.5f)

            swapDelayProgress.value assertEquals 0.5f
        }
    }

    @Test
    fun `onRepeat perform swap`() {
        val animator: Animator.Companion = mockk()
        val repeatSlot: CapturingSlot<() -> Unit> = slot()
        every {
            animator.loopingBetween(
                range = any(),
                durationMs = any(),
                onUpdate = any(),
                onRepeat = capture(repeatSlot),
            )
        } returns mockk(relaxed = true)

        val onGameActionListener: OnGameActionListener = mockk(relaxed = true)

        subject(
            animator = animator,
        ).apply {

            this.onGameActionListener = onGameActionListener

            resume()
            repeatSlot.captured()
        }

        verify {
            onGameActionListener.onSwapAction(any(), any())
        }
    }
}