package com.krishna.game2048.gameengine

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.krishna.game2048.utility.Utils

/*
class to draw canvas every X frame per second
 */
class GameHandler(var surfaceHolder: SurfaceHolder, val gameManager: GameManager) : Thread() {

    val targetFps = 60
    var canvas: Canvas? = null
    var isRunning = false
        set(value) {
            field = value
        }

    override fun run() {
        super.run()
        var startTime: Long = 0;
        var timeMillis: Long = 0;
        var waitTime: Long = 0
        var frameCount = 0
        val targetTime = 1000 / targetFps
        while (isRunning) {
            startTime = System.nanoTime()
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameManager.update()
                    gameManager.draw(canvas)
                }
            } catch (e: Exception) {
                Utils.log("${e.printStackTrace()}")
            } finally {
                try {
                    canvas?.let {
                        surfaceHolder.unlockCanvasAndPost(it)
                    }
                } catch (e: java.lang.Exception) {
                    Utils.log("${e.printStackTrace()}")
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis
            try {
                if (waitTime > 0)
                    sleep(waitTime)
            } catch (ex: Exception) {
            }
        }
    }

}