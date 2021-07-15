package com.zb.baselibs.utils.awesome.http

import com.zb.baselibs.utils.awesome.core.AwesomeDownloaderOption
import com.zb.baselibs.utils.awesome.core.controller.DownloadController
import com.zb.baselibs.utils.awesome.core.listener.IDownloadListener
import com.zb.baselibs.utils.awesome.database.TaskInfo
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object OkHttpManager {

    fun getClient(
        option: AwesomeDownloaderOption,
        downloadListener: IDownloadListener,
        downloadController: DownloadController
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ProgressInterceptor(downloadListener, downloadController))
            .connectTimeout(option.timeout, TimeUnit.SECONDS)
            .build()


    fun createRequest(taskInfo: TaskInfo): Request =
        Request.Builder().url(taskInfo.url)
            .addHeader("Range", "bytes=${taskInfo.downloadedBytes}-")
            .build()
}