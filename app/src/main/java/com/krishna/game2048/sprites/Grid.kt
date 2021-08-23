package com.krishna.game2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.krishna.game2048.R

class Grid(res: Resources, var scWidth: Int, var scHeight: Int, tileSize: Int) : Sprite {
    var grid: Bitmap
    init {
        val bmp = BitmapFactory.decodeResource(res, R.drawable.grid)
        grid = Bitmap.createScaledBitmap(bmp, tileSize * 4, tileSize * 4, false)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(
            grid,
            ((scWidth / 2).toFloat() - (grid.width / 2).toFloat()),
            (scHeight / 2).toFloat() - (grid.height / 2).toFloat(),
            null
        )
    }

    override fun update() {
        TODO("Not yet implemented")
    }
}