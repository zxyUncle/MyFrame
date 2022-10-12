package com.normal.zbase.logs

import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.flattener.ClassicFlattener
import com.elvishew.xlog.printer.AndroidPrinter
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

    private var mapPath = mutableMapOf<String, FilePrinter>()

    private fun getFilePrinter(pathKey: String): FilePrinter {
        return FilePrinter.Builder(pathKey) // Specify the directory path of log file(s)
            .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
            .backupStrategy(NeverBackupStrategy()) // Default: FileSizeBackupStrategy(1024 * 1024)
            .cleanStrategy(FileLastModifiedCleanStrategy(MAX_TIME)) // Default: NeverCleanStrategy()
            .flattener(ClassicFlattener()) // Default: DefaultFlattener
            .build()
    }

    init {
        initXLog()
        initFilePrinter()
        createDir(LogFilesPath.NORMAL)
        createDir(LogFilesPath.ERROR)
        createDir(LogFilesPath.OTHER)
        createDir(LogFilesPath.API)
    }
    //默认级别日志
    @JvmField
    val NORMAL = log_help.instance(mapPath[LogFilesPath.NORMAL]!!)
    //错误级别日志
    @JvmField
    val ERROR = log_help.instance(mapPath[LogFilesPath.ERROR]!!)
    //网路级别日志
    @JvmField
    val API = log_help.instance(mapPath[LogFilesPath.API]!!)
    //其他级别日志
    @JvmField
    val OTHER = log_help.instance(mapPath[LogFilesPath.OTHER]!!)

    /**
     * 日志的路径，第一个是默认日志的路径
     */
    private fun initFilePrinter() {
        mapPath[LogFilesPath.ERROR] = getFilePrinter(
            LogFilesPath.ERROR
        )
        mapPath[LogFilesPath.API] = getFilePrinter(
            LogFilesPath.API
        )
        mapPath[LogFilesPath.OTHER] = getFilePrinter(
            LogFilesPath.OTHER
        )
    }


    private fun initXLog() {
        val config: LogConfiguration = LogConfiguration.Builder()
            .logLevel(LogLevel.ALL)// Specify log level, logs below this level won't be printed, default: LogLevel.ALL)
            .tag(TAG) // Specify TAG, default: "X-LOG"
            .nst()
            .build()

        val androidPrinter =
            AndroidPrinter(true) // Printer that print the log using android.util.Log

        val filePrinter =
            FilePrinter.Builder(LogFilesPath.NORMAL) // Specify the directory path of log file(s)
                .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(NeverBackupStrategy()) // Default: FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(FileLastModifiedCleanStrategy(MAX_TIME)) // Default: NeverCleanStrategy()
                .flattener(ClassicFlattener()) // Default: DefaultFlattener
                .build()
        XLog.init(config, androidPrinter, filePrinter)
        mapPath[LogFilesPath.NORMAL] = filePrinter

    }


    private fun createDir(path: String = LogFilesPath.NORMAL) {
        // 检查日志文件夹
        val file = File(path)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ERROR.i("$path 目录创建失败")
                return
            }
        }
    }
}