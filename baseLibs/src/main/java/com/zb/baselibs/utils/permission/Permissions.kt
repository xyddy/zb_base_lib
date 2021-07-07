package com.zb.baselibs.utils.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zb.baselibs.utils.permission.callbacks.FailCallback
import com.zb.baselibs.utils.permission.callbacks.SuccessCallback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun FragmentActivity.requestPermissionsForResult(
    vararg permissions: String,
    title: String = "权限请求",
    rationale: String
): Boolean =
    suspendCoroutine {
        InlinePermissionResult(this)
            .onSuccess(object : SuccessCallback {
                override fun onSuccess() {
                    it.resume(true)
                }
            })
            .onFail(object : FailCallback {
                override fun onFailed() {
                    it.resumeWithException(InlineRequestPermissionException())
                }
            })
            .requestPermissions(title = title, rationale = rationale, permissions = *permissions)
    }

suspend fun Fragment.requestPermissionsForResult(
    vararg permissions: String,
    title: String = "权限请求",
    rationale: String
): Boolean =
    suspendCoroutine {
        InlinePermissionResult(this)
            .onSuccess(object : SuccessCallback {
                override fun onSuccess() {
                    it.resume(true)
                }
            })
            .onFail(object : FailCallback {
                override fun onFailed() {
                    it.resumeWithException(InlineRequestPermissionException())
                }
            })
            .requestPermissions(title = title, rationale = rationale, permissions = *permissions)
    }