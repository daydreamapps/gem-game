package com.daydreamapplications.gemgame.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
            val score: Int by remember { current }

            GemGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    GameView(this as Score)
                    Text(text = "Score: $score")
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