package com.zb.baselibs.utils.awesome.core.downloader

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zb.baselibs.utils.awesome.core.AwesomeDownloader
import com.zb.baselibs.utils.awesome.core.controller.DownloadController
import com.zb.baselibs.utils.awesome.core.listener.IDownloadListener
import com.zb.baselibs.utils.awesome.database.DownloadTaskManager
import com.zb.baselibs.utils.awesome.database.TaskInfo
import com.zb.baselibs.utils.awesome.http.OkHttpManager
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import java.util.concurrent.ConcurrentLinkedQueue

class DefaultDownloader(application: Application) : IDownloader, AndroidViewModel(application) {
    override var appContext: Context = application.applicationContext
    override val scope: CoroutineScope = viewModelScope
    override val downloadController: DownloadController by lazy { DownloadController() }
    override val downloadQueue: ConcurrentLinkedQueue<TaskInfo> by lazy { ConcurrentLinkedQueue<TaskInfo>() }
    override val taskManager: DownloadTaskManager = DownloadTaskManager(appContext)
    override var downloadingTask: TaskInfo? = null
    override val okHttpClient: OkHttpClient by lazy {
        OkHttpManager.getClient(AwesomeDownloader.option, downloadListener, downloadController)
    }
    override val downloadListener: IDownloadListener by lazy { createListener() }

    override fun close() {
        stopAll()
        downloadingTask = null
    }

    override fun onCleared() {
        super.onCleared()
        close()
    }
}