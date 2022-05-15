package com.daydreamapplications.gemgame.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daydreamapplications.gemgame.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).apply {

            lifecycleOwner = this@MainActivity

            setContentView(root)
        }
    }
}