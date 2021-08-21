package com.krishna.game2048

interface SwipeCallback {
    fun onSwipe(d:Direction)

    enum class Direction{
        LEFT,RIGHT,UP,DOWN
    }
}