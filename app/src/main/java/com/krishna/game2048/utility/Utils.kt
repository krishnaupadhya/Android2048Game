package com.krishna.game2048.utility

import android.util.Log

object Utils {
    const val TAG = "AppUtils"
    fun log(message: String, tag: String = TAG) {
        Log.d(TAG, message)
    }
}