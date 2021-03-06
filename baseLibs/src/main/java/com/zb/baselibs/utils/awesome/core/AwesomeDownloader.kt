package com.zb.baselibs.utils.awesome.core

import android.content.ComponentName
import android.content.ContextWrapper
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zb.baselibs.utils.awesome.core.downloader.DefaultDownloader
import com.zb.baselibs.utils.awesome.core.downloader.ForegroundServiceDownloader
import com.zb.baselibs.utils.awesome.core.downloader.IDownloader
import com.zb.baselibs.utils.awesome.database.TaskInfo
import com.zb.baselibs.utils.awesome.tool.TAG

object AwesomeDownloader {
    //设置
    val option by lazy { AwesomeDownloaderOption() }

    private lateinit var realDownloader: IDownloader

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            val binder = service as ForegroundServiceDownloader.DownloadServiceBinder
            realDownloader = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
        }
    }

    var onDownloadError: (Exception) -> Unit = {}
    var onDownloadProgressChange: (Long) -> Unit = {}
    var onDownloadStop: (Long, Long) -> Unit = { _: Long, _: Long -> }
    var onDownloadFinished: (String, String) -> Unit = { _: String, _: String -> }

    fun initWithDefaultMode(activity: AppCompatActivity) {
        realDownloader = DefaultDownloader(activity.application)
    }

    fun close(contextWrapper: ContextWrapper) {
        val serviceIntent = Intent(contextWrapper, ForegroundServiceDownloader::class.java)
        realDownloader.close()
        contextWrapper.apply {
            unbindService(serviceConnection)
            stopService(serviceIntent)
        }

    }

    fun enqueue(url: String, filePath: String, fileName: String): AwesomeDownloader {
        realDownloader.enqueue(url, filePath, fileName)
        return this
    }

    fun stopAll() {
        realDownloader.stopAll()
    }

    fun resume() {
        realDownloader.resumeAndStart()
    }

    fun cancelAll() {
        realDownloader.cancelAll()
    }

    fun cancel() {
        realDownloader.cancel()
    }

    fun clearCache(taskInfo: TaskInfo) {
        realDownloader.clearCache(taskInfo)
    }

    fun setOnError(onError: (Exception) -> Unit): AwesomeDownloader {
        onDownloadError = onError
        return this
    }

    fun setOnProgressChange(onProgressChange: (Long) -> Unit): AwesomeDownloader {
        onDownloadProgressChange = onProgressChange
        return this
    }

    fun setOnStop(onStop: (Long, Long) -> Unit): AwesomeDownloader {
        onDownloadStop = onStop
        return this
    }

    fun setOnFinished(onFinished: (String, String) -> Unit): AwesomeDownloader {
        onDownloadFinished = onFinished
        return this
    }


    /**
     * 获取当前下载队列所有任务信息的数组
     * @return Array<(TaskInfo?)>
     */
    fun getDownloadQueueArray() = realDownloader.getDownloadQueueArray()

    /**
     * 查询所有任务信息
     * @return MutableList<TaskInfo>
     */
    suspend fun queryAllTaskInfo(): MutableList<TaskInfo> = realDownloader.queryAllTaskInfo()

    /**
     * 查询未完成的任务信息
     * @return MutableList<TaskInfo>
     */
    suspend fun queryUnfinishedTaskInfo(): MutableList<TaskInfo> =
        realDownloader.queryUnfinishedTaskInfo()

    /**
     * 返回包含所有任务信息的LiveData
     * @return LiveData<List<TaskInfo>>
     */
    fun getAllTaskInfoLiveData() = realDownloader.getAllTaskInfoLiveData()

    /**
     * 返回包含未完成的任务信息的LiveData
     * @return LiveData<MutableList<TaskInfo>>
     */
    fun getUnfinishedTaskInfoLiveData() = realDownloader.getUnfinishedTaskInfoLiveData()

    /**
     *查询已完成的任务信息
     * @return MutableList<TaskInfo>
     */
    suspend fun queryFinishedTaskInfo() = realDownloader.queryFinishedTaskInfo()

    /**
     * 返回包含已完成的任务信息的LiveData
     * @return LiveData<MutableList<TaskInfo>>
     */
    fun getFinishedTaskInfoLiveData() = realDownloader.getFinishedTaskInfoLiveData()


}