@file:JvmName("LoadExtend")
@file:JvmMultifileClass
package com.normal.zbase.utils

import android.app.Activity
import android.content.Context
import com.normal.zbase.R
import com.normal.zbase.utils.tools.ApplicationUtils
import com.normal.zbase.utils.tools.LoadTools

/**
 * Created by zsf on 2021/1/11 15:51
 * ******************************************
 * *加载动画扩展
 * ******************************************
 */

fun Activity.showLoad(message:String= ApplicationUtils.context().resources.getString(R.string.refresh_header_loading)) {
    LoadTools.INSTANCE.show(this,message)
}

fun hideLoading() {
    LoadTools.INSTANCE.hide()
}
