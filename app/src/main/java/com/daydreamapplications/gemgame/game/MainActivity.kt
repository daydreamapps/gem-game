package com.daydreamapplications.gemgame.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daydreamapplications.gemgame.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Score {

    override val current: MutableStateFlow<Int> = MutableStateFlow(0)

    override fun change(by: Int) {
        current.value += by
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {

            lifecycleOwner = this@MainActivity

            setContentView(root)
        }
    }
}