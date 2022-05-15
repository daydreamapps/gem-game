package com.daydreamapplications.gemgame.game

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.daydreamapplications.gemgame.R
import java.util.*

enum class GemType(
    @DrawableRes private val resId: Int,
) {
    BLOOD(resId = R.drawable.ic_gem_ruby),
    COIN(resId = R.drawable.ic_gem_citrine),
    DIAMOND(resId = R.drawable.ic_gem_amethyst),
    EMERALD(resId = R.drawable.ic_gem_square),
    SAPPHIRE(resId = R.drawable.ic_gem_sapphire);

    fun drawable(resources: Resources): Drawable? {
        return ResourcesCompat.getDrawable(resources, resId, null)
    }
}

fun randomGemType(): GemType {
    val index = Random().nextInt(GemType.values().size)
    return GemType.values()[index]
}