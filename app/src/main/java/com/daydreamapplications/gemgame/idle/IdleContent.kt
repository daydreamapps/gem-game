package com.daydreamapplications.gemgame.idle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeView
import com.daydreamapplications.gemgame.idle.upgrades.UpgradesRepository

@Composable
fun IdleContent(
    idleController: IdleController,
    upgradesRepository: UpgradesRepository,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            IdleGameView(
                idleController = idleController,
            )
        }
        UpgradeView(
            modifier = Modifier.fillMaxWidth(),
            upgrades = upgradesRepository.upgrades.collectAsState(initial = emptyList()),
            onSelected = { upgrade ->
                // TODO: reduce score by upgrade cost
                // TODO: apply upgrade to controller
                upgradesRepository.removeUpgradeFromList(upgrade)
            },
        )
    }
}