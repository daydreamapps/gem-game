package com.daydreamapplications.gemgame.data.scores

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Score(
    @PrimaryKey
    val timeInMillis: Long,
    @ColumnInfo(name = "points") val points: Int,
)