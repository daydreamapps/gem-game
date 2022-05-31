package com.daydreamapplications.gemgame.idle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Score: ${score.current.value}")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            IdleGameView(
                idleController = idleController,
                idleGameConfig = idleGameConfig,
                score = score,
            )
        }

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