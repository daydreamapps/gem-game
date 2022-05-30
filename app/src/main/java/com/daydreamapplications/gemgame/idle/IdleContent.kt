package com.daydreamapplications.gemgame.idle

import androidx.compose.runtime.Composable

@Composable
fun IdleContent(
    idleController: IdleController,
) {
    IdleGameView(
        idleController = idleController,
    )
}