package com.daydreamapplications.gemgame.game

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daydreamapplications.gemgame.R
import com.daydreamapplications.gemgame.game.ui.GameView
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
            val context = LocalContext.current

            val score: Int by remember { current }

            GemGameTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.app_name)) },
                            actions = {
                                IconButton(
                                    onClick = { context.finishActivity() }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(end = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Quit")
                                        Text(text = "Quit")
                                    }
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
        }
    }

    companion object {

        fun startGame(context: Context) {
            Intent(context, GameActivity::class.java)
                .let(context::startActivity)
        }
    }
}

//TODO put somewhere better
fun Context?.finishActivity() {
    (this as? Activity)?.finish()
}