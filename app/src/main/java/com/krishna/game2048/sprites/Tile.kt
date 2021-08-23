package com.krishna.game2048.sprites

import android.graphics.Canvas
import java.util.*

class Tile(
    val tileSize: Int,
    val scWidth: Int,
    val scHeight: Int,
    val tmCallback: TileManagerCallback,
    val matrixX: Int,
    val matrixY: Int
) : Sprite {

    var curTileLevel: Int = 1//level that this tile is on
    var curX: Int = 0
    var curY: Int = 0
    var destX: Int = 0
    var destY: Int = 0
    var isMoving = false
    var movingSpeed = 100 //num of pixels per frame tile is moving
    var increment = false

    init {
        // (scWidth / 2 - 2 * tileSize) positions the tile in x direction i.e at 0,0 of grid
        // ( matrixY * tileSize ) is required to move it in x direction towards x,0 or x,1 or x,2 or x,4
        curX = scWidth / 2 - 2 * tileSize + matrixY * tileSize
        destX = curX
        // (scHeight / 2 - 2 * tileSize) positions the tile in Y direction i.e at 0,0 of grid
        // ( matrixX * tileSize ) is required to move it in Y direction  towards 0,y or 1,y or 2,y or 3,y
        curY = scHeight / 2 - 2 * tileSize + matrixX * tileSize
        destY = curY

        //generates tile with value 4, one in 10 times
        val chanceOf4 = Random().nextInt(100)
        if (chanceOf4 >= 90) {
            curTileLevel = 2
        }
    }

    fun move(matrixX: Int, matrixY: Int) {
        isMoving = true
        destX = scWidth / 2 - 2 * tileSize + matrixY * tileSize
        destY = scHeight / 2 - 2 * tileSize + matrixX * tileSize
    }

    //draw a tile on the curX and curY position
    //create a tile from tile manager
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(
            tmCallback.getBitMap(curTileLevel),
            curX.toFloat(),
            curY.toFloat(),
            null
        )
        if (isMoving && curX == destX && curY == destY) {
            // we want to know if tiles moving is complete so that we can increment count if 2 tiles are merged
            isMoving = false
            if (increment) {
                //on moving tiles and merging with existing tiles new tile value should be incremented to next value
                // ex, if 2 then 4, if 4 then 8, i.e increments in 2 to power n
                curTileLevel++
                increment = false
            }
            tmCallback.onFinishedMoving(this)
        }
    }

    /**
     * move curX to dest X with speed if tiles need to move large distance else just move without speed
     */
    override fun update() {
        if (curX < destX) {
            //curX may be very close to destX in that case we may get jitter if add more speed
            if (curX + movingSpeed > destX) {
                curX = destX //avoid adding speed
            } else {
                curX += movingSpeed //add speed
            }
        } else if (curX > destX) { // we are too far to the right
            if (curX - movingSpeed < destX) {
                curX = destX //
            } else {
                curX -= movingSpeed
            }
        }

        if (curY < destY) {
            //curY may be very close to destY in that case we may get jitter if add more speed
            if (curY + movingSpeed > destY) {
                curY = destY //avoid adding speed
            } else {
                curY += movingSpeed //add speed
            }
        } else if (curY > destY) { // we are too far to the right
            if (curY - movingSpeed < destY) {
                curY = destY //
            } else {
                curY -= movingSpeed
            }
        }
    }

    fun getValueTileLevel(): Int {
        return curTileLevel
    }

    fun incrementTile(): Tile {
        increment = true
        return this
    }

    fun toIncrement(): Boolean {
        return increment
    }
}