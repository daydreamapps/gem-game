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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.daydreamapplications.gemgame.ui.theme.GemGameTheme
import kotlinx.coroutines.delay

class IdleActivity : AppCompatActivity() {

    companion object {

        fun startActivity(context: Context) {
            Intent(context, IdleActivity::class.java)
                .let(context::startActivity)
        }
    }

    private val idleController: IdleController = IdleController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            while (true) {
                delay(2000)
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    idleController.move()
                }
            }
        }

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
                        IdleContent(idleController)
                    }
                }
            }
        }
    }
}