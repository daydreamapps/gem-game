package com.daydreamapplications.gemgame.data.scores

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao
}