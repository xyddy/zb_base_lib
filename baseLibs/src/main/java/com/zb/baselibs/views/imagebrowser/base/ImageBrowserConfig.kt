package com.zb.baselibs.views.imagebrowser.base

import com.zb.baselibs.views.imagebrowser.listener.ImageEngine
import com.zb.baselibs.views.imagebrowser.listener.OnClickListener
import com.zb.baselibs.views.imagebrowser.listener.OnLongClickListener
import java.util.*

open class ImageBrowserConfig {
    //枚举类型
    enum class TransformType {
        TransformDefault, TransformDepthPage, TransformRotateDown, TransformRotateUp, TransformZoomIn, TransformZoomOutSlide, TransformZoomOut
    }

    var position: Int = 0
    var transformType: TransformType = TransformType.TransformDefault
    var imageList = ArrayList<String>()
    var imageEngine: ImageEngine? = null
    var onClickListener: OnClickListener? = null
    var onLongClickListener: OnLongClickListener? = null

    fun show(startBack: StartBack) {
        //判断是不是空
        if (imageList.size <= 0) {
            return
        }
        if (imageEngine == null) {
            return
        }
        startBack.onStartActivity()
    }

    interface StartBack {
        fun onStartActivity()
    }
}