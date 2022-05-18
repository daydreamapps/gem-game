package com.daydreamapplications.gemgame.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.daydreamapplications.gemgame.ui.theme.GemGameTheme

@Composable
fun QuitDialog(
    properties: DialogProperties = DialogProperties(),
    onDismiss: () -> Unit = {},
    onQuit: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties,
    ) {
        QuitDialogContent(
            onDismiss = onDismiss,
            onQuit = onQuit,
        )
    }
}

@Composable
fun QuitDialogContent(
    onDismiss: () -> Unit = {},
    onQuit: () -> Unit = {},
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
                text = "Are you sure?",
                fontWeight = FontWeight.Bold,
            )
            Divider(
                modifier = Modifier.padding(8.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = onQuit,
            ) {
                Text(text = "End")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(),
            ) {
                Text(text = "Resume")
            }
        }
    }
}

@Composable
@Preview
fun PreviewQuitDialog() {
    GemGameTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            QuitDialogContent()
        }
    }
}