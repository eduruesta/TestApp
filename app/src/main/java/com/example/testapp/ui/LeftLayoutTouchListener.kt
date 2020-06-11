package com.example.testapp.ui

import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class LeftLayoutTouchListener(swipeRefreshLayout: SwipeRefreshLayout) : View.OnTouchListener {

    companion object {
        const val logTag = "ActivitySwipeDetector"
        const val MIN_DISTANCE = 100
    }

    private var swipeRefresh: SwipeRefreshLayout = swipeRefreshLayout
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f

    fun onRightToLeftSwipe() {
       swipeRefresh.visibility = View.GONE
        swipeRefresh.animate().duration = 1000

    }

    fun onLeftToRightSwipe() {
        swipeRefresh.visibility = View.VISIBLE
        swipeRefresh.animate().duration = 1000
    }


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                upX = event.x
                upY = event.y
                val deltaX = downX - upX
                val deltaY = downY - upY

                // swipe horizontal?
                if (kotlin.math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        onLeftToRightSwipe()
                        return true
                    }
                    if (deltaX > 0) {
                        onRightToLeftSwipe()
                        return true
                    }
                } else {
                    Log.i(
                        logTag,
                        "Swipe was only " + kotlin.math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE
                    )
                    // return false; // We don't consume the event
                }


                return false // We don't consume the event
            }
        }
        return false
    }
}


