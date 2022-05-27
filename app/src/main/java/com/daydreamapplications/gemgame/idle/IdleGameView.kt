package com.daydreamapplications.gemgame.idle

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.daydreamapplications.gemgame.game.Score
import com.daydreamapplications.gemgame.game.ui.GameView

@Composable
fun IdleGameView(
    idleController: IdleController,
    score: Score? = null,
) {
    AndroidView(factory = {
        GameView(it).also {
            it.score = score
            it.idleController = idleController
        }
    })
}