package com.zb.baselibs.utils

import com.zb.baselibs.R
import com.zb.baselibs.app.BaseApp


object ObjectUtils {

    // 线条颜色
    @JvmStatic
    fun getLineWhite(): Int {
        return R.color.white
    }

    // 线条颜色
    @JvmStatic
    fun getLineEFE(): Int {
        return R.color.black_efe
    }

    // 线条颜色
    @JvmStatic
    fun getLineE5(): Int {
        return R.color.black_e5
    }

    @JvmStatic
    // 根据屏幕宽获取尺寸
    fun getViewSizeByWidth(scale: Float): Int {
        return BaseApp.W.times(scale).toInt()
    }

    @JvmStatic
    // 根据屏幕高获取尺寸
    fun getViewSizeByHeight(scale: Float): Int {
        return BaseApp.H.times(scale).toInt()
    }

    // 根据屏幕宽获取尺寸
    @JvmStatic
    fun getViewSizeByWidthFromMax(width: Int): Int {
        return (BaseApp.W.times(width.toFloat()) / 1080f).toInt()
    }

    // 根据屏幕高获取尺寸
    @JvmStatic
    fun getViewSizeByHeightFromMax(height: Int): Int {
        return (BaseApp.H.times(height.toFloat()) / 1920f).toInt()
    }

    // 显示手机号
    @JvmStatic
    fun getPhone(phone: String): String? {
        return if (phone.length < 11)
            phone
        else
            phone.substring(0, 3) + " " + phone.substring(3, 7) + " " + phone.substring(7)
    }

    // 验证码
    @JvmStatic
    fun getCodeWidth(): Int {
        return ((BaseApp.W - BaseApp.context.dip2px(106f)) / 4)
    }
}