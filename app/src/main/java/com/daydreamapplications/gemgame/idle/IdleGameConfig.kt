package com.daydreamapplications.gemgame.idle

import com.daydreamapplications.gemgame.game.GameConfig
import javax.inject.Inject

class IdleGameConfig @Inject constructor(
) : GameConfig {

    override val width: Int = 8
    override val height: Int = 6
}