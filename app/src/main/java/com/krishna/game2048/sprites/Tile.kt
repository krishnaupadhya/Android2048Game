package com.krishna.game2048.sprites

import android.graphics.Canvas

class Tile(
    val tileSize: Int,
    val scWidth: Int,
    val scHeight: Int,
    val tmCallback: TileManagerCallback
) : Sprite {

    var curTileLevel: Int = 1//level that this tile is on

    //draw a tile on the correct position
    //create a tile from tile manager
    //
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(
            tmCallback.getBitMap(curTileLevel),
            (scWidth / 2).toFloat() - tileSize,
            (scHeight / 2).toFloat() - tileSize,
            null
        )
    }

    override fun update() {
        TODO("Not yet implemented")
    }
}