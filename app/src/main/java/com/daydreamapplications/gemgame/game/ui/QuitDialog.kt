package com.daydreamapplications.gemgame.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun QuitDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onQuit: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Are you sure?")
        },
        buttons = {
            Text(text = "Progress will be lost")
        },
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(onClick = onQuit) {
                    Text(text = "End")
                }
                Button(onClick = onDismiss) {
                    Text(text = "Resume")
                }
            }
        },
    )
}