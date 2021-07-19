package com.zb.baselibs.utils

import android.animation.ObjectAnimator
import android.os.SystemClock
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zb.baselibs.R
import com.zb.baselibs.app.BaseApp
import com.zb.baselibs.databinding.ToastViewBinding
import org.jaaksi.pickerview.util.Util.dip2px

object ToastUtil {
    private lateinit var translateY: ObjectAnimator

    /**  toastType：  0：普通Toast  1：居中  2：置顶  */
    fun showToast(activity: AppCompatActivity, text: CharSequence, toastType: Int) {
        if (toastType == 0) {
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
        } else {
            val mBinding: ToastViewBinding =
                DataBindingUtil.inflate(activity.layoutInflater, R.layout.toast_view, null, false)
            mBinding.content = text.toString()
            mBinding.toastType = toastType
            val toast = Toast(activity)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = mBinding.root //添加视图文件
            if (toastType == 2) {
                toast.setGravity(Gravity.TOP, 0, -dip2px(activity, 75f))
                translateY = ObjectAnimator.ofFloat(
                    mBinding.toastLinear,
                    "translationY",
                    -dip2px(activity, 75f).toFloat(),
                    0F
                ).setDuration(500)
                if (!translateY.isRunning) {
                    translateY.start()
                    BaseApp.fixedThreadPool.execute {
                        SystemClock.sleep(2000)
                        activity.runOnUiThread(translateY::cancel)
                    }
                }
            } else {
                toast.setGravity(Gravity.CENTER, 0, 0)
            }
            toast.show()
        }
    }
}