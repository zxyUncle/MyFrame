package com.normal.zbase.utils.obj

import android.os.Environment
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
import com.normal.zbase.utils.extend.gson
import com.normal.zbase.utils.tools.ApplicationUtils
import java.io.File

/**
 * Created by zsf on 2022/8/18 16:29
 * *******************
 *    日志类
 * *******************
 */
object LoggerUtils {
    private const val MAX_TIME = (10 * 24 * 60 * 60 * 1000).toLong()
//    private var logPath = ApplicationUtils.context().filesDir.absolutePath+"/xlog/"
//    private var logPath = "/mnt/sdcard/Dowload/%s/logs/"
    private var logPath = Environment.getExternalStorageDirectory().path +"/Download/%s/logs"
    private var TAG="HTTP"

    init {
        init()
    }

    @JvmStatic
    fun init() {
        createDir()
        initHTTP()
    }
    @JvmStatic
    fun i(msg: String) {
        XLog.i(msg)
    }

    @JvmStatic
    fun i(tag:String,msg: String) {
        XLog.tag(tag).i(msg)
    }

    @JvmStatic
    fun e(e: Exception) {
        XLog.e(e)
    }

    @JvmStatic
    fun e(msg:String,e: Throwable) {
        XLog.e(msg,e)
    }

    @JvmStatic
    fun json(obj: Any) {
        val toJson  = gson.toJson(obj)
        XLog.i(toJson)
    }

    private fun initHTTP() {
        logPath = String.format(logPath, ApplicationUtils.context().packageName)
        val config: LogConfiguration = LogConfiguration.Builder()
            .logLevel(LogLevel.ALL)// Specify log level, logs below this level won't be printed, default: LogLevel.ALL)
            .tag(TAG) // Specify TAG, default: "X-LOG"
            .disableStackTrace()
            .build()


        val androidPrinter = AndroidPrinter(true) // Printer that print the log using android.util.Log
        val consolePrinter = ConsolePrinter() // Printer that print the log to console using System.out
        val filePrinter = FilePrinter.Builder(logPath) // Specify the directory path of log file(s)
                .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(NeverBackupStrategy()) // Default: FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(FileLastModifiedCleanStrategy(MAX_TIME)) // Default: NeverCleanStrategy()
                .flattener(ClassicFlattener()) // Default: DefaultFlattener
                .build()

        XLog.init(config, androidPrinter, filePrinter)
    }

    /**
     * 检查日志文件夹
     */
    private fun createDir() {

        val file = File(logPath)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(TAG, "目录创建失败")
                return
            }
        }
    }
}