package com.krishna.game2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.krishna.game2048.R

class GameOver(res: Resources, var scWidth: Int, var scHeight: Int) : Sprite {

    var gameOverBmp: Bitmap

    init {
        val bmp = BitmapFactory.decodeResource(res, R.drawable.gameover)
        gameOverBmp = Bitmap.createScaledBitmap(
            bmp,
            res.getDimension(R.dimen.dimen_300).toInt(),
            res.getDimension(R.dimen.dimen_75).toInt(),
            false
        )
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(
            gameOverBmp, (scWidth / 2 - gameOverBmp.width / 2).toFloat(),
            (scHeight / 2 - gameOverBmp.height / 2).toFloat(),
            null
        )
    }

    override fun update() {
        TODO("Not yet implemented")
    }
}