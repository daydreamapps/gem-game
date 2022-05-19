package com.daydreamapplications.gemgame.data.scores

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Query("SELECT * FROM score")
    fun getAll(): LiveData<List<Score>>

    @Insert
    suspend fun insert(score: Score)

    @Delete
    suspend fun delete(score: Score)
}