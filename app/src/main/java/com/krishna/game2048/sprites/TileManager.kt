package com.krishna.game2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.krishna.game2048.R

class TileManager(
    val res: Resources,
    val tileSize: Int,
    val scWidth: Int,
    val scHeight: Int
) : TileManagerCallback, Sprite {
    lateinit var tile: Tile

    val drawables = mutableListOf<Int>()

    //all possible tiles holder
    val tileMap = hashMapOf<Int, Bitmap>()

    init {
        initBitmaps()
        tile = Tile(tileSize, scWidth, scHeight, this)
    }


    private fun initBitmaps() {
        drawables.add(R.drawable.one)
        drawables.add(R.drawable.two)
        drawables.add(R.drawable.three)
        drawables.add(R.drawable.four)
        drawables.add(R.drawable.five)
        drawables.add(R.drawable.six)
        drawables.add(R.drawable.seven)
        drawables.add(R.drawable.eight)
        drawables.add(R.drawable.nine)
        drawables.add(R.drawable.ten)
        drawables.add(R.drawable.eleven)
        drawables.add(R.drawable.twelve)
        drawables.add(R.drawable.thirteen)
        drawables.add(R.drawable.fourteen)
        drawables.add(R.drawable.fifteen)
        drawables.add(R.drawable.sixteen)

        //TODO: this addition could take a while, better to add progress bar until all bitmaps are created
        for ((index, item) in drawables.withIndex()) {
            val bmp = BitmapFactory.decodeResource(res, item)
            //create tile of tile size using bitmap
            val tileBmp = Bitmap.createScaledBitmap(bmp, tileSize, tileSize, false)
            tileMap.put(index, tileBmp)
        }
    }

    override fun draw(canvas: Canvas) {
        tile.draw(canvas)
    }

    override fun update() {
    }

    override fun getBitMap(curTileLevel: Int): Bitmap {
        return tileMap[curTileLevel-1] ?: kotlin.run {
            val bmp = BitmapFactory.decodeResource(res, drawables[curTileLevel-1])
            //create tile of tile size using bitmap
            Bitmap.createScaledBitmap(bmp, tileSize, tileSize, false)
        }

    }
}