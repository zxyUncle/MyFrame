package com.normal.zbase.logs

import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.flattener.ClassicFlattener
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.normal.zbase.http.domain.ApiConfig
import java.io.File

/**
 * Created by zsf on 2022/8/18 16:29
 * *******************
 *    日志类
 * *******************
 */
object Logs {
    private const val MAX_TIME = (7 * 24 * 60 * 60 * 1000).toLong()
    private var TAG = ApiConfig.HTTP_TAG

    private var mapPath = mutableMapOf<String, Array<Printer>>()

    private val logConfiguration: LogConfiguration = LogConfiguration.Builder()
        .logLevel(LogLevel.ALL)// Specify log level, logs below this level won't be printed, default: LogLevel.ALL)
        .tag(TAG) // Specify TAG, default: "X-LOG"
        .nst()
        .build()

    init {
        createDir(LogFilesPath.NORMAL, LogFilesPath.ERROR, LogFilesPath.OTHER, LogFilesPath.API)
        initXLog(LogFilesPath.NORMAL, LogFilesPath.ERROR, LogFilesPath.OTHER, LogFilesPath.API)
    }

    //默认级别日志
    @JvmStatic
    val NORMAL: HelpLog by lazy {
        HelpLog(*mapPath[LogFilesPath.NORMAL]!!)
    }

    //错误日志
    @JvmStatic
    val ERROR: HelpLog by lazy {
        HelpLog(*mapPath[LogFilesPath.ERROR]!!)
    }

    //网路级别日志
    @JvmStatic
    val API: HelpLog by lazy {
        HelpLog(*mapPath[LogFilesPath.API]!!)
    }

    //其他级别日志
    @JvmStatic
    val OTHER: HelpLog by lazy {
        HelpLog(*mapPath[LogFilesPath.OTHER]!!)
    }

    //初始化日志
    private fun initXLog(vararg path: String) {
        for (index in path) {
            val androidPrinter =
                AndroidPrinter(true) // Printer that print the log using android.util.Log

            val filePrinter =
                FilePrinter.Builder(index) // Specify the directory path of log file(s)
                    .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
                    .backupStrategy(NeverBackupStrategy()) // Default: FileSizeBackupStrategy(1024 * 1024)
                    .cleanStrategy(FileLastModifiedCleanStrategy(MAX_TIME)) // Default: NeverCleanStrategy()
                    .flattener(ClassicFlattener()) // Default: DefaultFlattener
                    .build()
            val listPrinter = arrayOf(androidPrinter, filePrinter)
            XLog.init(logConfiguration, *listPrinter)
            mapPath[index] = listPrinter
        }


    }

    //创建日志目录
    private fun createDir(vararg path: String) {
        for (index in path) {
            // 检查日志文件夹
            val file = File(index)
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    ERROR.i("$index 目录创建失败")
                    return
                }
            }
        }
    }
}