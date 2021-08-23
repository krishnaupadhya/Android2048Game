package com.krishna.game2048.gameengine

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.krishna.game2048.SwipeCallback
import com.krishna.game2048.SwipeListener
import com.krishna.game2048.sprites.GameOver
import com.krishna.game2048.sprites.Grid
import com.krishna.game2048.sprites.Score
import com.krishna.game2048.sprites.TileManager


class GameManager(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback, SwipeCallback, GameStateCallback {
    lateinit var gameHandler: GameHandler
    lateinit var grid: Grid
    var scHeight: Int = 0
    var scWidth: Int = 0
    var tileSize: Int = 0
    var mContext: Context? = null
    var tileManager: TileManager
    var swipeListener: SwipeListener
    private var isGameOver = false
    private var gameOver: GameOver
    private var score: Score

    init {
        holder.addCallback(this)
        isLongClickable = true // allows us to intercept the on touch event
        mContext = context
        swipeListener = SwipeListener(mContext, this)
        initGrid()
        tileManager = TileManager(resources, tileSize, scWidth, scHeight, this)
        gameOver = GameOver(resources, scWidth, scHeight)
        score = Score(resources, scWidth, scHeight, tileSize)

    }

    fun initGame() {
        isGameOver = false
        tileManager.initGame()
    }

    private fun initGrid() {
        val dm = DisplayMetrics()
        (mContext as Activity).windowManager.defaultDisplay.getMetrics(dm)
        scHeight = dm.heightPixels
        scWidth = dm.widthPixels
        //tile size is 4 times less of screen width, 0.88 is added for padding
        tileSize = ((scWidth * 0.88) / 4).toInt()
        grid = Grid(resources, scWidth, scHeight, tileSize)

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameHandler = GameHandler(holder, this).apply {
            isRunning = true
            start()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        gameHandler.surfaceHolder = holder
    }

    //put app to b/g to f/g background thread is destroyed, when we return same bg thread should be running
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                gameHandler.apply {
                    isRunning = false
                    join()
                }
                retry = false
            } catch (e: InterruptedException) {

            }
        }
    }

    fun update() {
        if (isGameOver.not()) {
            tileManager.update()
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.let {
            it.drawRGB(250, 250, 250)
            grid.draw(it)
            tileManager.draw(it)
            score.draw(canvas)
            if (isGameOver)
                gameOver.draw(it)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isGameOver && event?.action == MotionEvent.ACTION_DOWN) {
            initGame()
        }
        event?.let {
            swipeListener.onTouchEvent(event)
        }
        return super.onTouchEvent(event)
    }

    override fun onSwipe(d: SwipeCallback.Direction) {
        //tile manager processes the direction
        tileManager.onSwipe(d)
    }

    override fun gameOver() {
        isGameOver = true
    }

    override fun updateScore(delta: Int) {
        score.updateScore(delta)
    }
}