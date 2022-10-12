package com.normal.zbase.logs

import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.file.FilePrinter
import com.normal.zbase.utils.extend.gson

/**
 * Created by zsf on 2022/10/12 14:45
 * ******************************************
 * *
 * ******************************************
 */
class log_help {
    var filePrinter: FilePrinter

    constructor(filePrinter: FilePrinter) {
        this.filePrinter = filePrinter
    }

    //zxy 单例模式
    companion object {
        @Volatile
        private var obj: log_help? = null

        fun instance(filePrinter: FilePrinter): log_help {
            if (obj == null) {
                synchronized(log_help::class.java) {
                    if (obj == null) {
                        obj = log_help(filePrinter)
                    }
                }
            }
            return obj!!
        }
    }

    /**
     * @param logPathKey 可以忽略 指定日志的输入路径 LogFilesPath,不指定使用默认路径
     */
    fun json(obj: Any) {
        val toJson = gson.toJson(obj)
        XLog.printers(filePrinter).json(toJson)
    }

    /**
     * @param logPathKey 可以忽略 指定日志的输入路径 LogFilesPath,不指定使用默认路径
     */
    fun e(e: Exception) {
        XLog.printers(filePrinter).e(e)
    }

    /**
     * @param logPathKey 可以忽略 指定日志的输入路径 LogFilesPath ,不指定使用默认路径
     */
    fun i(msg: String = "") {
        XLog.printers(filePrinter).i(msg)
    }


    /**
     * @param logPathKey 可以忽略 指定日志的输入路径 LogFilesPath,不指定使用默认路径
     */
    fun e(throwable: Throwable) {
        XLog.printers(filePrinter).e(throwable)
    }
}