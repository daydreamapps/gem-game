package com.daydreamapplications.gemgame.idle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daydreamapplications.gemgame.game.Score

@Composable
fun IdleGameContent(
    modifier: Modifier = Modifier,
    score: Score,
    idleController: IdleController,
    idleGameConfig: IdleGameConfig,
) {
    Surface(modifier = modifier) {
        Column {

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Score: ${score.current.value}")
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                IdleGameView(
                    idleController = idleController,
                    idleGameConfig = idleGameConfig,
                    score = score,
                )
            }
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = idleController.swapDelayProgress.value,
            )
        }
    }
}