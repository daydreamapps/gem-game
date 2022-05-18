package com.daydreamapplications.gemgame.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daydreamapplications.gemgame.R
import com.daydreamapplications.gemgame.game.ui.GameView
import com.daydreamapplications.gemgame.game.ui.QuitDialog
import com.daydreamapplications.gemgame.ui.theme.GemGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameActivity : AppCompatActivity(), Score {

    override val current: MutableState<Int> = mutableStateOf(0)

    override fun change(by: Int) {
        current.value += by
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val score: Int by remember { current }
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
                    }
                }
            }

            if (quitDialogShown) {
                QuitDialog(
                    onDismiss = { quitDialogShown = false },
                    onQuit = ::finish,
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