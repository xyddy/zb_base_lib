package com.zb.baselibs.utils.awesome.core.downloader

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.zb.baselibs.utils.awesome.core.AwesomeDownloader
import com.zb.baselibs.utils.awesome.core.controller.DownloadController
import com.zb.baselibs.utils.awesome.core.listener.IDownloadListener
import com.zb.baselibs.utils.awesome.database.DownloadTaskManager
import com.zb.baselibs.utils.awesome.database.TaskInfo
import com.zb.baselibs.utils.awesome.http.OkHttpManager
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class ForegroundServiceDownloader : LifecycleService(), IDownloader {
    val tag = "ForegroundService"
    override lateinit var appContext: Context
    override val scope: CoroutineScope = lifecycleScope
    override val downloadController: DownloadController by lazy { DownloadController() }
    override val downloadQueue: Queue<TaskInfo> by lazy { ConcurrentLinkedQueue() }
    override val taskManager: DownloadTaskManager by lazy { DownloadTaskManager(appContext) }
    override var downloadingTask: TaskInfo? = null
    override val okHttpClient: OkHttpClient by lazy {
        OkHttpManager.getClient(
            AwesomeDownloader.option,
            downloadListener,
            downloadController
        )
    }
    override val downloadListener: IDownloadListener by lazy { createListener() }

    override fun close() {
        stopAll()
        downloadingTask = null
        stopSelf()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return DownloadServiceBinder()
    }

    inner class DownloadServiceBinder : Binder() {
        fun getService() = this@ForegroundServiceDownloader
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(tag, "onStartCommand: ")
        return START_NOT_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(tag, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy: ")
    }
}