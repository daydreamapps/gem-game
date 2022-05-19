package com.daydreamapplications.gemgame.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.daydreamapplications.gemgame.R
import com.daydreamapplications.gemgame.data.scores.ScoreDatabase
import com.daydreamapplications.gemgame.game.ui.EndGameDialog
import com.daydreamapplications.gemgame.game.ui.GameView
import com.daydreamapplications.gemgame.game.ui.QuitDialog
import com.daydreamapplications.gemgame.ui.theme.GemGameTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GameActivity : AppCompatActivity(), Score {

    @Inject
    lateinit var scoreDatabase: ScoreDatabase

    override val current: MutableState<Int> = mutableStateOf(0)

    private val isGameComplete: MutableState<Boolean> = mutableStateOf(false)
    private val timeRemainingInSeconds: MutableState<Int> = mutableStateOf(60)

    override fun change(by: Int) {
        current.value += by
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            while (timeRemainingInSeconds.value > 0) {
                delay(1000)
                timeRemainingInSeconds.value--
            }
            isGameComplete.value = true
            cancel()
        }

        setContent {
            val score: Int by remember { current }
            val gameComplete: Boolean by remember { isGameComplete }
            val secondsRemaining: Int by remember { timeRemainingInSeconds }

            var quitDialogShown: Boolean by remember { mutableStateOf(false) }

            GemGameTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.app_name)) },
                            actions = {
                                IconButton(
                                    onClick = {
                                        quitDialogShown = true
                                    }
                                ) {
                                    Text(text = "End Game")
                                }
                            },
                        )
                    },
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding())
                            .padding(8.dp),
                        color = MaterialTheme.colors.background
                    ) {
                        GameView(this as Score)
                        Text(text = "Score: $score")
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = "Time Remaining: $secondsRemaining")
                    }
                }
            }

            if (quitDialogShown) {
                QuitDialog(
                    onDismiss = { quitDialogShown = false },
                    onQuit = ::finish,
                )
            }

            if (gameComplete) {
                EndGameDialog(
                    score = score,
                    onConfirm = {
                        lifecycleScope.launch {
                            scoreDatabase.scoreDao().insert(
                                com.daydreamapplications.gemgame.data.scores.Score(
                                    timeInMillis = System.currentTimeMillis(),
                                    points = score,
                                )
                            )
                            finish()
                        }
                    },
                )
            }
        }
    }

    companion object {

        fun startGame(context: Context) {
            Intent(context, GameActivity::class.java)
                .let(context::startActivity)
        }
    }
}