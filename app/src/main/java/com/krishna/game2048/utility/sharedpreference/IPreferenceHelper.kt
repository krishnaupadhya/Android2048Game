package com.krishna.game2048.utility.sharedpreference

interface IPreferenceHelper {

    fun clearPreference()


    fun getTopScore(): Int
    fun setTopScore(score: Int)
}