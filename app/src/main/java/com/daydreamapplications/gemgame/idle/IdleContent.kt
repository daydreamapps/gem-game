package com.daydreamapplications.gemgame.idle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.daydreamapplications.gemgame.game.Score
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeView
import com.daydreamapplications.gemgame.idle.upgrades.UpgradesRepository

@Composable
fun IdleContent(
    idleController: IdleController,
    idleGameConfig: IdleGameConfig,
    score: Score,
    upgradesRepository: UpgradesRepository,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        IdleGameContent(
            modifier = Modifier.weight(1f),
            score = score,
            idleController = idleController,
            idleGameConfig = idleGameConfig
        )

        UpgradeView(
            modifier = Modifier.fillMaxWidth(),
            upgrades = upgradesRepository.upgrades.collectAsState(initial = emptyList()),
            score = score,
            onSelected = { upgrade ->
                score.change(-upgrade.cost)
                idleGameConfig.upgrade(upgrade)
                upgradesRepository.removeUpgradeFromList(upgrade)
            },
        )
    }
}

@Composable
private fun IdleGameContent(
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