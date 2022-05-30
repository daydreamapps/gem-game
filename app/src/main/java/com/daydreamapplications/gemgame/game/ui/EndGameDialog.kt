package com.daydreamapplications.gemgame.game.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun EndGameDialog(
    score: Number,
    onConfirm: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        EndDialogContent(
            score = score,
            onConfirm = onConfirm,
        )
    }
}

@Composable
fun EndDialogContent(
    score: Number,
    onConfirm: () -> Unit = {},
) {
    Card(
        modifier = Modifier.padding(32.dp),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Game Complete",
                fontWeight = FontWeight.Bold,
            )
            Text(text = "Total score: $score")

            Divider(modifier = Modifier.padding(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = onConfirm,
            ) {
                Text(text = "Confirm")
            }
        }
    }
}
