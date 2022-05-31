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
    idleGameConfig: IdleGameConfig,
    score: Score? = null,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            GameView(context).also {
                it.score = score
                it.gameConfig = idleGameConfig
                it.idleController = idleController
            }
        }
    )
}