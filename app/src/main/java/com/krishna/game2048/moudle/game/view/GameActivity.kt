package com.krishna.game2048.moudle.game.view

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.krishna.game2048.R
import com.krishna.game2048.moudle.game.viewmodel.GameViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.game_activity)
    }
}