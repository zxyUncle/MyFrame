package com.normal.zbase.manager

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.normal.zbase.utils.extend.requestPermission

/**
 * Created by zsf on 2022/8/19 17:17
 * ******************************************
 * * 权限管理
 * ******************************************
 */
object PermissionManager {
    //写内存
    @JvmStatic
    var WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    //读内存
    @JvmStatic
    var READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    /**
     * kotlin 直接调用 requestPermission 有扩展函数
     */
    @JvmStatic
    fun reqeustPermission(appCompatActivity: AppCompatActivity,list:MutableList<String>, onSuccess: () -> Unit, onFailed: (MutableList<String>) -> Unit) {
        appCompatActivity.requestPermission(list,onSuccess,onFailed)
    }
}