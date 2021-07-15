package com.zb.baselibs.utils.awesome.core.controller

class DownloadController {
    private var workState =
        WorkState.RUNNING
    @Synchronized
    fun pause() {
        workState = WorkState.STOP
    }

    @Synchronized
    fun start() {
        workState = WorkState.RUNNING
    }

    fun isPause() = workState == WorkState.STOP
}

enum class WorkState {
    RUNNING, STOP
}