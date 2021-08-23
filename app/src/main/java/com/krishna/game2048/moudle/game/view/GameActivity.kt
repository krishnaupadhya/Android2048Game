package com.krishna.game2048.moudle.game.view

import android.app.Activity
import android.os.Bundle
import android.view.Window
import com.krishna.game2048.R

class GameActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.game_activity)
    }
}