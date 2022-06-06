package com.daydreamapplications.gemgame.idle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.daydreamapplications.gemgame.game.Score
import com.daydreamapplications.gemgame.idle.upgrades.UpgradeView
import com.daydreamapplications.gemgame.idle.upgrades.UpgradesRepository
import kotlinx.coroutines.launch

@Composable
fun IdleContent(
    idleController: IdleController,
    score: Score,
    upgradesRepository: UpgradesRepository,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        IdleGameContent(
            modifier = Modifier.weight(1f),
            score = score,
            idleController = idleController,
        )

        UpgradeView(
            modifier = Modifier.fillMaxWidth(),
            upgrades = upgradesRepository.upgrades.collectAsState(initial = emptyList()),
            score = score,
            onSelected = { upgrade ->
                lifecycleOwner.lifecycleScope.launch {
                    score.change(-upgrade.cost)
                    idleController.applyUpgrade(upgrade)
                    upgradesRepository.removeUpgradeFromList(upgrade)
                }
            },
        )
    }
}