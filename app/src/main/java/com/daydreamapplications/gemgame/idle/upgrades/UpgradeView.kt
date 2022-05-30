package com.daydreamapplications.gemgame.idle.upgrades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.daydreamapplications.gemgame.game.Score

@Composable
fun UpgradeView(
    modifier: Modifier = Modifier,
    upgrades: State<List<Upgrade>>,
    score: Score,
    onSelected: (Upgrade) -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.secondary,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
        ) {
            Text(text = "Upgrades", modifier = Modifier.padding(8.dp))
            LazyRow(
                modifier = modifier,
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(items = upgrades.value) {
                        UpgradeButton(
                            upgrade = it,
                            score = score,
                            onClick = onSelected,
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun UpgradeButton(
    upgrade: Upgrade,
    score: Score,
    onClick: (Upgrade) -> Unit,
) {
    Card(
        modifier = Modifier.size(width = 150.dp, height = 150.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = upgrade.type.title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text(text = "${upgrade.multiplier}", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
            Button(
                modifier = Modifier.weight(1f),
                enabled = upgrade.cost < score.current.value,
                onClick = { onClick(upgrade) }
            ) {
                Column {
                    Text(text = "Upgrade\n${upgrade.cost}", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
