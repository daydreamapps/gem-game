package com.daydreamapplications.gemgame.idle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daydreamapplications.gemgame.idle.score.ScoreRepository
import com.daydreamapplications.gemgame.idle.upgrades.UpgradesRepository
import com.daydreamapplications.gemgame.ui.theme.GemGameTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IdleActivity : AppCompatActivity() {

    companion object {

        fun startActivity(context: Context) {
            Intent(context, IdleActivity::class.java)
                .let(context::startActivity)
        }
    }

    private val idleController: IdleController = IdleController()

    @Inject
    lateinit var idleGameConfig: IdleGameConfig

    @Inject
    lateinit var scoreRepository: ScoreRepository

    @Inject
    lateinit var upgradesRepository: UpgradesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GemGameTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Idle Gems") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Navigate Back")
                                }
                            },
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding())
                            .padding(8.dp),
                        color = MaterialTheme.colors.background
                    ) {
                        IdleContent(idleController, idleGameConfig, scoreRepository, upgradesRepository)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        idleController.resume()
    }

    override fun onPause() {
        super.onPause()
        idleController.pause()
    }
}