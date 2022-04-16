package com.daydreamapplications.gemgame

import java.util.*

enum class GemType {
    BLOOD,
    COIN,
    DIAMOND,
    EMERALD,
    SAPPHIRE
}

fun randomGemType(): GemType {
    val index = Random().nextInt(GemType.values().size)
    return GemType.values()[index]
}