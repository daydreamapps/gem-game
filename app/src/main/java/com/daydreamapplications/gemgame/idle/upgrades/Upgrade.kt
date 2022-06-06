package com.daydreamapplications.gemgame.idle.upgrades

data class Upgrade(
    val id: Int,
    val cost: Long,
    val type: UpgradeType,
    val multiplier: Double,
)
