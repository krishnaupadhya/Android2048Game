package com.krishna.game2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.krishna.game2048.R
import com.krishna.game2048.SwipeCallback
import com.krishna.game2048.utility.Utils
import java.util.*
import kotlin.collections.ArrayList

class TileManager(
    private val res: Resources,
    private val tileSize: Int,
    private val scWidth: Int,
    private val scHeight: Int,

    ) : TileManagerCallback, Sprite {

    private val drawables = mutableListOf<Int>()

    //all possible tiles holder
    private val tileMap = hashMapOf<Int, Bitmap>()

    //initalizing 2d arrya of tiles martix 4*4
    private var tilesMatrix: Array<Array<Tile?>> = Array(4) {
        Array(4) {
            null
        }
    }

    var isMoving = false

    lateinit var movingTiles: ArrayList<Tile>


    init {
        initBitmaps()
        initGame()
    }

    private fun initGame() {
        var index = 0;
        movingTiles = ArrayList()
        // create 5 tiles on random positions on game start
        while (index < 5) {
            val x = Random().nextInt(4)
            val y = Random().nextInt(4)
            if (tilesMatrix[x][y] == null) {
                tilesMatrix[x][y] = Tile(tileSize, scWidth, scHeight, this, x, y)
                index++
            } else {
                index--
            }
        }

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
        for (i in 0..3) {
            for (j in 0..3) {
                if (tilesMatrix[i][j] != null) {
                    tilesMatrix[i][j]?.draw(canvas)
                }
            }
        }
    }

    override fun update() {
        for (i in 0..3) {
            for (j in 0..3) {
                if (tilesMatrix[i][j] != null) {
                    tilesMatrix[i][j]?.update()
                }
            }
        }
    }

    override fun getBitMap(curTileLevel: Int): Bitmap {
        return tileMap[curTileLevel - 1] ?: kotlin.run {
            val bmp = BitmapFactory.decodeResource(res, drawables[curTileLevel - 1])
            //create tile of tile size using bitmap
            Bitmap.createScaledBitmap(bmp, tileSize, tileSize, false)
        }

    }

    fun onSwipe(d: SwipeCallback.Direction) {
        if (!isMoving) { // to prevent swiping multiple times while one action is in progress
            isMoving = true
            var tempMatrix: Array<Array<Tile?>> = Array(4) {
                Array(4) {
                    null
                }
            }
            when (d) {
                SwipeCallback.Direction.UP -> handleSwipeUpLogic(tempMatrix)
                SwipeCallback.Direction.DOWN -> handleSwipeDownLogic(tempMatrix)
                SwipeCallback.Direction.LEFT -> handleSwipeLeftLogic(tempMatrix)
                else -> handleSwipeRightLogic(tempMatrix)
            }
            tilesMatrix = tempMatrix
        }
    }

    private fun handleSwipeRightLogic(tempMatrix: Array<Array<Tile?>>) {

        for (i in 0..3) {
            for (j in 3 downTo 0) {
                if (tilesMatrix[i][j] != null) {
                    tempMatrix[i][j] =
                        tilesMatrix[i][j] // copy same tile in new matrix at same position
                    for (k in j + 1..3){
                        if (tempMatrix[i][k] == null) { //if tile at one position right is null
                            tempMatrix[i][k] = tilesMatrix[i][j] // move element one position right
                            if (tempMatrix[i][k - 1] == tilesMatrix[i][j])
                                tempMatrix[i][k - 1] =
                                    null //since we moved element one pos right, empty prev position space
                        }//need function to retrieve a new value and function to increment tile value
                        //if tiles at current pos in tilesMatrix and one position right tempMatrix are same
                        //need to crete new tile with incremented value
                        else if (tempMatrix[i][k]?.getValueTileLevel() == tilesMatrix[i][j]?.getValueTileLevel()
                            && tempMatrix[i][k]?.toIncrement()?.not() == true
                        ) {
                            //increment tile to next value
                            tempMatrix[i][k] = tilesMatrix[i][j]?.incrementTile()
                            if (tempMatrix[i][k - 1] == tilesMatrix[i][j])
                                tempMatrix[i][k - 1] =
                                    null //since we moved element one pos right, empty prev position space

                        } else {
                            break
                        }
                    }
                }
            }
        }

        for (i in 0..3) {
            for (j in  3 downTo 0) {
                val t = tilesMatrix[i][j]
                var newT: Tile? = null
                var matrixX = 0
                var matrixY = 0
                //go through new matrix and check if tiles is present in new matrix, and find position
                for (a in 0..3) {
                    for (b in 0..3) {
                        if (tempMatrix[a][b] == t) {
                            newT = tempMatrix[a][b]
                            matrixX = a
                            matrixY = b
                            break
                        }

                    }
                }
                // move tile to correct position
                newT?.let {
                    movingTiles.add(newT)
                    t?.move(matrixX, matrixY)
                }
            }
        }

    }

    private fun handleSwipeLeftLogic(tempMatrix: Array<Array<Tile?>>) {
        for (i in 0..3) {
            for (j in 0..3) {
                if (tilesMatrix[i][j] != null) {
                    tempMatrix[i][j] =
                        tilesMatrix[i][j] // copy same tile in new matrix at same position
                    for (k in j - 1 downTo 0) { //iterate through matrix from left to right
                        if (tempMatrix[i][k] == null) { //if tile at one position left is null
                            tempMatrix[i][k] = tilesMatrix[i][j] // move element one position left
                            if (tempMatrix[i][k + 1] == tilesMatrix[i][j])
                                tempMatrix[i][k + 1] =
                                    null //since we moved element one pos up, empty prev position space
                        }
                        //need function to retrieve a new value and function to increment tile value
                        //if tiles at current pos in tilesMatrix and one position left tempMatrix are same
                        //need to crete new tile with incremented value
                        else if (tempMatrix[i][k]?.getValueTileLevel() == tilesMatrix[i][j]?.getValueTileLevel()
                            && tempMatrix[i][k]?.toIncrement()?.not() == true
                        ) {
                            //increment tile to next value
                            tempMatrix[i][k] = tilesMatrix[i][j]?.incrementTile()
                            if (tempMatrix[i][k + 1] == tilesMatrix[i][j])
                                tempMatrix[i][k + 1] =
                                    null //since we moved element one pos up, empty prev position space

                        } else {
                            break
                        }
                    }
                }
            }
        }

        for (i in 3 downTo 0) {
            for (j in 0..3) {
                val t = tilesMatrix[i][j]
                var newT: Tile? = null
                var matrixX = 0
                var matrixY = 0
                //go through new matrix and check if tiles is present in new matrix, and find position
                for (a in 0..3) {
                    for (b in 0..3) {
                        if (tempMatrix[a][b] == t) {
                            newT = tempMatrix[a][b]
                            matrixX = a
                            matrixY = b
                            break
                        }

                    }
                }
                // move tile to correct position
                newT?.let {
                    movingTiles.add(newT)
                    t?.move(matrixX, matrixY)
                }
            }
        }
    }

    private fun handleSwipeDownLogic(tempMatrix: Array<Array<Tile?>>) {
        for (i in 3 downTo 0) {
            for (j in 0..3) {
                if (tilesMatrix[i][j] != null) {
                    tempMatrix[i][j] =
                        tilesMatrix[i][j] // copy same tile in new matrix at same position
                    for (k in i + 1..3) { //iterate through matrix from up to down
                        if (tempMatrix[k][j] == null) { //if tile at one position up is null
                            tempMatrix[k][j] = tilesMatrix[i][j] // move element one position up
                            if (tempMatrix[k - 1][j] == tilesMatrix[i][j])
                                tempMatrix[k - 1][j] =
                                    null //since we moved element one pos down, empty prev position space
                        }
                        //need function to retrieve a new value and function to increment tile value
                        //if tiles at current pos in tilesMatrix and one position down tempMatrix are same
                        //need to crete new tile with incremented value
                        else if (tempMatrix[k][j]?.getValueTileLevel() == tilesMatrix[i][j]?.getValueTileLevel()
                            && tempMatrix[k][j]?.toIncrement()?.not() == true
                        ) {
                            //increment tile to next value
                            tempMatrix[k][j] = tilesMatrix[i][j]?.incrementTile()
                            Utils.log("uplog tempMatrix[k][j] - ${tempMatrix[k][j]}")
                            if (tempMatrix[k - 1][j] == tilesMatrix[i][j])
                                tempMatrix[k - 1][j] =
                                    null //since we moved element one pos up, empty prev position space

                        } else {
                            break
                        }
                    }
                }
            }
        }

        for (i in 3 downTo 0) {
            for (j in 0..3) {
                val t = tilesMatrix[i][j]
                var newT: Tile? = null
                var matrixX = 0
                var matrixY = 0
                //go through new matrix and check if tiles is present in new matrix, and find position
                for (a in 0..3) {
                    for (b in 0..3) {
                        if (tempMatrix[a][b] == t) {
                            newT = tempMatrix[a][b]
                            matrixX = a
                            matrixY = b
                            break
                        }

                    }
                }
                // move tile to correct position
                newT?.let {
                    movingTiles.add(newT)
                    t?.move(matrixX, matrixY)
                }
            }
        }
    }

    /**
     * on swipe up iterate through matrix from bottom to top to see if there are any elements already present
     */
    private fun handleSwipeUpLogic(tempMatrix: Array<Array<Tile?>>) {
        for (i in 0..3) {
            Utils.log("uplog -----------------start--------------------")
            Utils.log("uplog i - $i")
            for (j in 0..3) {
                Utils.log("uplog i,j - $i,$j")
                if (tilesMatrix[i][j] != null) {
                    tempMatrix[i][j] =
                        tilesMatrix[i][j] // copy same tile in new matrix at same position
                    Utils.log("uplog tilesMatrix[i][j]- ${tilesMatrix[i][j]}")

                    for (k in i - 1 downTo 0) { //iterate through matrix from bottom to top
                        Utils.log("uplog k,i - $k,$i")
                        if (tempMatrix[k][j] == null) { //if tile at one position up is null
                            tempMatrix[k][j] = tilesMatrix[i][j] // move element one position up
                            Utils.log("uplog tempMatrix[k][j] - ${tilesMatrix[i][j]}")
                            Utils.log("uplog tempMatrix[k + 1][j] - ${tempMatrix[k + 1][j]}")
                            if (tempMatrix[k + 1][j] == tilesMatrix[i][j])
                                tempMatrix[k + 1][j] =
                                    null //since we moved element one pos up, empty prev position space
                        }
                        //need function to retrieve a new value and function to increment tile value
                        //if tiles at current pos in tilesMatrix and one position up tempMatrix are same
                        //need to crete new tile with incremented value
                        else if (tempMatrix[k][j]?.getValueTileLevel() == tilesMatrix[i][j]?.getValueTileLevel()
                            && tempMatrix[k][j]?.toIncrement()?.not() == true
                        ) {
                            //increment tile to next value
                            tempMatrix[k][j] = tilesMatrix[i][j]?.incrementTile()
                            Utils.log("uplog tempMatrix[k][j] - ${tempMatrix[k][j]}")
                            if (tempMatrix[k + 1][j] == tilesMatrix[i][j])
                                tempMatrix[k + 1][j] =
                                    null //since we moved element one pos up, empty prev position space

                        } else {
                            break
                        }
                    }
                }
            }
            Utils.log("uplog i - $i")
            Utils.log("uplog -----------------end--------------------")
        }
        //now we have new positions of tile in tempMatrix and old positions in tilesMatrix
        //
        for (i in 0..3) {
            for (j in 0..3) {
                val t = tilesMatrix[i][j]
                var newT: Tile? = null
                var matrixX = 0
                var matrixY = 0
                //go through new matrix and check if tiles is present in new matrix, and find position
                for (a in 0..3) {
                    for (b in 0..3) {
                        if (tempMatrix[a][b] == t) {
                            newT = tempMatrix[a][b]
                            matrixX = a
                            matrixY = b
                            break
                        }

                    }
                }
                // move tile to correct position
                newT?.let {
                    movingTiles.add(newT)
                    t?.move(matrixX, matrixY)
                }
            }
        }
    }

}
