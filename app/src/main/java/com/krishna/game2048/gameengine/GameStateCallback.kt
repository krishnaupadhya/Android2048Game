package com.krishna.game2048.gameengine

interface GameStateCallback {
    fun gameOver()
    fun updateScore(delta: Int)
}