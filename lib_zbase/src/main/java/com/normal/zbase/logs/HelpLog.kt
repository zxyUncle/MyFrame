package com.normal.zbase.logs

import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.Printer
import com.normal.zbase.utils.extend.gson

/**
 * Created by zsf on 2022/10/12 14:45
 * ******************************************
 * *
 * ******************************************
 */
class HelpLog {
    private var printers = arrayOf<Printer>()


    constructor(vararg printers: Printer) {
        this.printers = printers as Array<Printer>
    }

    /**
     * 指定日志的输入路径 LogFilesPath,不指定使用默认路径
     */
    fun json(obj: Any) {
        val toJson = gson.toJson(obj)
        XLog.printers(*printers).json(toJson)
    }


    /**
     *  指定日志的输入路径 LogFilesPath ,不指定使用默认路径
     */
    fun i(msg: String = "") {
        XLog.printers(*printers).i(msg)
    }

    /**
     *  指定日志的输入路径 LogFilesPath,不指定使用默认路径
     */
    fun e(e: Exception) {
        XLog.printers(*printers).e(e)
    }

    /**
     *  指定日志的输入路径 LogFilesPath,不指定使用默认路径
     */
    fun e(throwable: Throwable) {
        XLog.printers(*printers).e(throwable)
    }
}