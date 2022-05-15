package com.daydreamapplications.gemgame.game

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.daydreamapplications.gemgame.databinding.ActivityGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GameActivity : AppCompatActivity(), Score {

    override val current: MutableStateFlow<Int> = MutableStateFlow(0)

    override fun change(by: Int) {
        current.value += by
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ActivityGameBinding.inflate(layoutInflater).apply {

            lifecycleOwner = this@GameActivity
            gemGridView.score = this@GameActivity

            observeCurrentScore(scoreValue)

            setContentView(root)
        }
    }

    private fun observeCurrentScore(textView: TextView) {
        lifecycleScope.launchWhenResumed {
            current.collect {
                textView.text = "Score: $it"
            }
        }
    }
}