package com.daydreamapplications.gemgame.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.daydreamapplications.gemgame.R
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

        supportActionBar?.setSubtitle("text")
        supportActionBar?.setSubtitle("text")

        ActivityGameBinding.inflate(layoutInflater).apply {

            lifecycleOwner = this@GameActivity
            gemGridView.score = this@GameActivity

            observeCurrentScore(scoreValue)

            setContentView(root)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_quit -> {
                finish()
                true
            }
            else -> false
        }
    }

    private fun observeCurrentScore(textView: TextView) {
        lifecycleScope.launchWhenResumed {
            current.collect {
                textView.text = "Score: $it"
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