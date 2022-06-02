package com.daydreamapplications.gemgame.idle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.daydreamapplications.gemgame.game.Score
import com.daydreamapplications.gemgame.game.ui.GameView

@Composable
fun IdleGameView(
    modifier: Modifier = Modifier,
    idleController: IdleController,
    score: Score? = null,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            GameView(
                context = context,
                immutableGameConfig = idleController.gameConfig,
            ).also {
                it.score = score
                it.idleController = idleController
            }
        }
    )
}