package com.zb.baselibs.views.autopoll

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class ScrollSpeedLinearLayoutManger(private val contxt: Context) :
    LinearLayoutManager(contxt) {
    private var MILLISECONDS_PER_INCH = 0.03f
    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val linearSmoothScroller: LinearSmoothScroller =
            object : LinearSmoothScroller(recyclerView.context) {
                override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                    return this@ScrollSpeedLinearLayoutManger.computeScrollVectorForPosition(
                        targetPosition
                    )
                }

                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    setSpeedSlow()
                    return MILLISECONDS_PER_INCH / displayMetrics.density
                }
            }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    fun setSpeedSlow() {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
        MILLISECONDS_PER_INCH = contxt.resources.displayMetrics.density * 3f
    }

    fun setSpeedFast() {
        MILLISECONDS_PER_INCH = contxt.resources.displayMetrics.density * 0.03f
    }
}