package com.krishna.game2048.sprites

import android.graphics.Bitmap

interface TileManagerCallback {
    fun getBitMap(curTileLevel: Int): Bitmap
    fun onFinishedMoving(t: Tile)
    fun updateScore(delta:Int)
}
