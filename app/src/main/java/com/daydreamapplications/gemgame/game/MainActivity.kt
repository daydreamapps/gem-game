package com.daydreamapplications.gemgame.game

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.daydreamapplications.gemgame.data.scores.Score
import com.daydreamapplications.gemgame.data.scores.ScoreDatabase
import com.daydreamapplications.gemgame.game.ui.Leaderboard
import com.daydreamapplications.gemgame.ui.theme.GemGameTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var scoreDatabase: ScoreDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            val score: State<List<Score>> = scoreDatabase.scoreDao().getAll().observeAsState(initial = emptyList())

            GemGameTheme {
                Scaffold(
                    bottomBar = {
                        BottomAppBar {
                            Leaderboard(score)
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = it.calculateTopPadding())
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { GameActivity.startGame(context) }
                            ) {
                                Text(text = "Start Game")
                            }
                        }
                    }
                }
            }
        }
    }
}