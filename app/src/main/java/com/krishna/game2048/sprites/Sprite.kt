package com.krishna.game2048.sprites

import android.graphics.Canvas

interface Sprite {
    fun draw(canvas: Canvas)
    fun update()
}