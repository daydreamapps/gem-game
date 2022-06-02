package com.daydreamapplications.gemgame.idle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daydreamapplications.gemgame.game.Score

@Composable
fun IdleGameContent(
    modifier: Modifier = Modifier,
    score: Score,
    idleController: IdleController,
) {
    Surface(modifier = modifier) {
        Column {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(text = "Score: ${score.current.value}")
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                IdleGameView(
                    idleController = idleController,
                    score = score,
                )
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .size(height = 8.dp, width = 0.dp),
                progress = idleController.swapDelayProgress.value,
            )
        }
    }
}