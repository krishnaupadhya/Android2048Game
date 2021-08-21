package com.krishna.game2048.gameengine

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder

import android.view.SurfaceView
import com.krishna.game2048.utility.Utils


class GameManager(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {
    lateinit var gameHandler: GameHandler

    init {
        holder.addCallback(this)
    }
    fun initGame() {}

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
        Utils.log("on updated called")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Utils.log("on onDraw called")


    }
}