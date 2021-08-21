package com.krishna.game2048

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent

class SwipeListener(val context: Context?,val swipeCallback: SwipeCallback) :GestureDetector.OnGestureListener {
    var mContext: Context? = null

    var gestureDetector: GestureDetector = GestureDetector(context,this)
    init {

    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    //this is called when user has flig motion rather than tap or click
    //X>Y motion is in upward direction else downward
    override fun onFling(
        originalMotion: MotionEvent?,
        endMotion: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        //
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if(velocityX>0){
                swipeCallback.onSwipe(SwipeCallback.Direction.RIGHT)
            }else{
                swipeCallback.onSwipe(SwipeCallback.Direction.LEFT)
            }
        }else{
            if(velocityY>0){
                swipeCallback.onSwipe(SwipeCallback.Direction.DOWN)
            }else{
                swipeCallback.onSwipe(SwipeCallback.Direction.UP)
            }
        }
        return false
    }

    fun onTouchEvent(e: MotionEvent) {
        gestureDetector.onTouchEvent(e)
    }
}