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

@Composable
fun UpgradeView(
    modifier: Modifier = Modifier,
    upgrades: State<List<Upgrade>>,
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
                        UpgradeButton(it, onSelected)
                    }
                }
            )
        }
    }
}

@Composable
fun UpgradeButton1(upgrade: Upgrade, onClick: (Upgrade) -> Unit) {
    Button(
        modifier = Modifier.size(width = 150.dp, height = 100.dp),
        onClick = { onClick(upgrade) }
    ) {
        Column {
            Text(text = upgrade.type.title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Text(text = "${upgrade.multiplier}", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun UpgradeButton(upgrade: Upgrade, onClick: (Upgrade) -> Unit) {
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
                onClick = { onClick(upgrade) }
            ) {
                Column {
                    Text(text = "Upgrade\n100K", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
