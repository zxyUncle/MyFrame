@file:JvmName("LoggerExtend")
@file:JvmMultifileClass
package com.normal.zbase.utils.extend

import com.normal.zbase.BuildConfig
import com.normal.zbase.utils.obj.Logger

/**
 * Created by zsf on 2021/1/11 14:25
 * ******************************************
 * * 自定义日志
 * ******************************************
 */
interface ZLogger {
    val loggerTag: String get() = getTag(javaClass)
}
private fun getTag(clazz: Class<*>) = clazz.simpleName.let {
    if (it.length <= 23) it else it.substring(0, 23)
}

private fun isDebug() = BuildConfig.DEBUG

fun ZLogger.printString(message: String="") {
    if(isDebug())
        Logger.i(loggerTag,message)
}


