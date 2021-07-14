package com.zb.baselibs.views.imagebrowser.base

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zb.baselibs.app.BaseApp
import com.zb.baselibs.views.imagebrowser.listener.ImageEngine
import com.zb.baselibs.views.imagebrowser.listener.OnClickListener
import com.zb.baselibs.views.imagebrowser.listener.OnLongClickListener

object MNImage {

    var imageBrowserConfig = ImageBrowserConfig()

    fun imageBrowser(
        sourceImageList: ArrayList<String>,
        index: Int,
        transformType: ImageBrowserConfig.TransformType?,
        callBack: ImageBrowserConfig.StartBack,
        clickListener: OnClickListener?,
        longClickListener: OnLongClickListener?
    ) {
        if (clickListener != null) {
            if (longClickListener != null) {
                imageBrowserConfig.imageList = sourceImageList
                imageBrowserConfig.position = index
                if (transformType != null)
                    imageBrowserConfig.transformType = transformType
                imageBrowserConfig.onClickListener = clickListener
                imageBrowserConfig.onLongClickListener = longClickListener
                imageBrowserConfig.imageEngine = object : ImageEngine {
                    override fun loadImage(context: Context, url: String, imageView: ImageView) {
                        val cropOptions = RequestOptions().fitCenter()
                        Glide.with(context).asBitmap().load(url).apply(cropOptions)
                            .into(object : SimpleTarget<Bitmap?>() {
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap?>?,
                                ) {
                                    val width = resource.width
                                    val height = resource.height
                                    val para: ViewGroup.LayoutParams = imageView.layoutParams
                                    if (width >= height) {
                                        para.width = BaseApp.W
                                        para.height =
                                            (resource.height.toFloat() * BaseApp.W / resource.width
                                                .toFloat()).toInt()
                                    } else {
                                        para.width =
                                            (resource.width.toFloat() * BaseApp.H / resource.height
                                                .toFloat()).toInt()
                                        para.height = BaseApp.H
                                    }
                                    imageView.layoutParams = para
                                    imageView.setImageBitmap(resource)
                                }
                            })
                    }
                }
                imageBrowserConfig.show(callBack)
            }
        }
    }
}