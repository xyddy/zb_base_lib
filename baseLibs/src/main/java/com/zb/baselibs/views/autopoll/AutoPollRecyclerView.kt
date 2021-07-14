package com.zb.baselibs.views.autopoll

import android.content.Context
import android.util.AttributeSet
import android.view.ViewConfiguration
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zb.baselibs.adapter.BindingItemAdapter
import java.lang.ref.WeakReference

class AutoPollRecyclerView(context: Context?, attrs: AttributeSet?) :
    RecyclerView(context!!, attrs) {
    var autoPollTask: AutoPollTask
    private var running = false//标示是否正在自动轮询
    private val mTouchSlop: Int

    /**
     * 持续滑动（走马灯）
     */
    class AutoPollTask(reference: AutoPollRecyclerView) : Runnable {
        private val mReference: WeakReference<AutoPollRecyclerView> = WeakReference(reference)
        override fun run() {
            val recyclerView = mReference.get()
            if (recyclerView != null && recyclerView.running) {
                recyclerView.scrollBy(0, 2)
                recyclerView.postDelayed(recyclerView.autoPollTask, 10L)
            }
        }

    }

    //开启:如果正在运行,先停止->再开启
    fun start() {
        if (running) stop()
        running = true
        postDelayed(autoPollTask, 10L)
    }

    fun stop() {
        running = false
        removeCallbacks(autoPollTask)
    }

    init {
        autoPollTask = AutoPollTask(this)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }
}
