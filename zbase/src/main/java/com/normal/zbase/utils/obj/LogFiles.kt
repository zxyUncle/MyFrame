package com.normal.zbase.utils.obj

import android.util.Log
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.flattener.ClassicFlattener
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.google.gson.Gson
import com.normal.zbase.utils.tools.ApplicationUtils
import java.io.File

/**
 * Created by zsf on 2021/7/1 11:19
 * ******************************************
 * *
 * ******************************************
 */
object LogFiles {
    private const val MAX_TIME = (3 * 24 * 60 * 60 * 1000).toLong()
    private var logPath = ApplicationUtils.context().filesDir.absolutePath+"/xlog/"
    private var TAG="logcat"

    init {
        init()
    }

    @JvmStatic
    fun init() {
        createDir()
        initXLog()
    }
    @JvmStatic
    fun i(msg: String) {
        XLog.i(msg)
    }
    @JvmStatic
    fun json(obj: Any) {
        val toJson = Gson().toJson(obj)
        XLog.i(toJson)
    }

    private fun initXLog() {
        val config: LogConfiguration = LogConfiguration.Builder()
            .logLevel(LogLevel.ALL)// Specify log level, logs below this level won't be printed, default: LogLevel.ALL)
            .tag(TAG) // Specify TAG, default: "X-LOG"
            .nst()
            .build()


        val androidPrinter =
            AndroidPrinter(4063) // Printer that print the log using android.util.Log
        val consolePrinter =
            ConsolePrinter() // Printer that print the log to console using System.out
        val filePrinter =
            FilePrinter.Builder(logPath) // Specify the directory path of log file(s)
                .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(NeverBackupStrategy()) // Default: FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(FileLastModifiedCleanStrategy(MAX_TIME)) // Default: NeverCleanStrategy()
                .flattener(ClassicFlattener()) // Default: DefaultFlattener
                .build()

        XLog.init(config, androidPrinter, consolePrinter, filePrinter)

    }

    private fun createDir() {
        // 检查日志文件夹
        val file = File(logPath)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return
            } else {
                Log.e("zxy", "目录创建成功")
            }
        } else {
            Log.e("zxy", "目录存在")
        }
    }
}