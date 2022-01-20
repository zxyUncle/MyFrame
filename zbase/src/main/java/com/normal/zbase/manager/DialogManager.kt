package com.normal.zbase.manager

import com.zxy.zxydialog.AlertDialogUtils

/**
 * Created by zsf on 2022/1/17 19:28
 * *******************
 *    Dialog 公共管理类
 * *******************
 */
object DialogManager {
    //默认样式弹出框
    fun normal(title:String,message:String){
        AlertDialogUtils.build(ActivityManager.getActivityManager().currentActivity())
            .setValues(title,message)
            .show()
    }
}