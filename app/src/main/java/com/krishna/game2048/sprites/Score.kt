package com.krishna.game2048.sprites

import android.content.res.Resources
import android.graphics.*
import com.krishna.game2048.R
import com.krishna.game2048.utility.sharedpreference.IPreferenceHelper
import org.koin.core.KoinComponent
import org.koin.core.inject

class Score(val res: Resources, var scWidth: Int, var scHeight: Int, var tileSize: Int) : Sprite,
    KoinComponent {
    private val sharedPreferences: IPreferenceHelper by inject()

    var scoreBmp: Bitmap
    var topScoreBmp: Bitmap
    var score = 0
    var topScore = 0
    private var paint: Paint

    init {
        topScore = sharedPreferences.getTopScore()
        val bmp = BitmapFactory.decodeResource(res, R.drawable.score)

        scoreBmp = Bitmap.createScaledBitmap(
            bmp,
            res.getDimension(R.dimen.dimen_100).toInt(),
            res.getDimension(R.dimen.dimen_15).toInt(),
            false
        )

        val tcBmp = BitmapFactory.decodeResource(res, R.drawable.topscore)
        topScoreBmp = Bitmap.createScaledBitmap(
            tcBmp,
            res.getDimension(R.dimen.dimen_100).toInt(),
            res.getDimension(R.dimen.dimen_15).toInt(),
            false
        )

        paint = Paint().apply {
            color = Color.BLACK
            textSize = res.getDimension(R.dimen.text_size_16)
        }

    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(
            scoreBmp,
            (scWidth / 4 - scoreBmp.width / 2).toFloat(),
            (scoreBmp.height).toFloat(),
            null
        )

        canvas.drawBitmap(
            topScoreBmp,
            3 * (scWidth / 4 - scoreBmp.height / 2).toFloat(),
            (scoreBmp.height).toFloat(),
            null
        )

        val w1 = paint.measureText(score.toString())
        val w2 = paint.measureText(topScore.toString())

        canvas.drawText(
            score.toString(),
            (scWidth / 4 - w1 / 2),
            (scoreBmp.height).toFloat() * 4,
            paint
        )

        canvas.drawText(
            topScore.toString(),
            3 * (scWidth / 4 - w1 / 2),
            (scoreBmp.height).toFloat() * 4,
            paint
        )
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    fun updateScore(delta: Int) {
        score += delta
        checkTopScore()
    }

    fun checkTopScore() {
        if (topScore < score) {
            sharedPreferences.setTopScore(score)
            topScore = score
        }

    }
}