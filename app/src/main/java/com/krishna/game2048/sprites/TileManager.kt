package com.krishna.game2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.krishna.game2048.R
import com.krishna.game2048.SwipeCallback

class TileManager(
    private val res: Resources,
    private val tileSize: Int,
    private val scWidth: Int,
    private val scHeight: Int,

    ) : TileManagerCallback, Sprite {
    private var tile: Tile

    private val drawables = mutableListOf<Int>()

    //all possible tiles holder
    private val tileMap = hashMapOf<Int, Bitmap>()

    //initalizing 2d arrya of tiles martix 4*4
    private var tilesMatrix : Array<Array<Tile?>> = Array(4) {
        Array(4) {
            Tile(tileSize, scWidth, scHeight, this, 1, 1)
        }
    }//arrayOf()

    init {


        initBitmaps()
        tile = Tile(tileSize, scWidth, scHeight, this,1,1)
//        initTiles(tilesMatrix)
        tilesMatrix[1][1] = tile
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
        tile.update()
    }

    override fun getBitMap(curTileLevel: Int): Bitmap {
        return tileMap[curTileLevel - 1] ?: kotlin.run {
            val bmp = BitmapFactory.decodeResource(res, drawables[curTileLevel - 1])
            //create tile of tile size using bitmap
            Bitmap.createScaledBitmap(bmp, tileSize, tileSize, false)
        }

    }

    fun onSwipe(d: SwipeCallback.Direction) {
        when (d) {
            SwipeCallback.Direction.UP -> tile.move(0, 1)
            SwipeCallback.Direction.DOWN -> tile.move(3, 1)
            SwipeCallback.Direction.LEFT -> tile.move(1, 0)
            else -> tile.move(1, 3)
        }
    }
}