package com.normal.zbase.logs;

import android.os.Environment;

import com.normal.zbase.utils.tools.ApplicationUtils;

/**
 * ApplicationUtils.context().filesDir.absolutePath+"/xlog/"
 * /mnt/sdcard/Dowload/%s/logs/"
 * Environment.getExternalStorageDirectory().getPath() + "/Download/
 */
public @interface LogFilesPath {
    String PARENT = Environment.getExternalStorageDirectory().getPath() + "/Download/" + ApplicationUtils.context().getPackageName() + "/";
//    String PARENT = Environment.getStorageDirectory().+ "/logs/" + ApplicationUtils.context().getPackageName() + "/";
    //日志级别目录
    String NORMAL = PARENT + "logs"; //默认路径
    String ERROR = PARENT + "logsErr";//一般存放错误日志的路径
    String API = PARENT + "logsAPi";//无所谓
    String OTHER = PARENT + "logsOther";//随便

}
