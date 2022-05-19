package com.daydreamapplications.gemgame.game.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.daydreamapplications.gemgame.data.scores.Score

@Composable
fun Leaderboard(
    score: State<List<Score>>,
) {
    val scores: List<Score> by remember { score }

    LazyColumn {
        items(
            items = scores.sortedByDescending { it.points }
        ) {
            Text(text = it.points.toString())
        }
    }
}