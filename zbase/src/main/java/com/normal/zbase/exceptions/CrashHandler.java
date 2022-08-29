package com.normal.zbase.exceptions;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.normal.zbase.utils.obj.LoggerUtils;

/**
 * Created by zsf on 2022/8/18 16:49
 * *******************
 *    全局异常拦截器
 * *******************
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    /**
     * CrashHandler实例的对 象
     */
    private static final CrashHandler INSTANCE = new CrashHandler();

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * init 创建
     */
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 主线程的尝试性质的crash抓取
        new Handler(Looper.getMainLooper()).post(() -> {
            //主线程异常拦截
            while (true) {
                try {
                    // 由于主线程的Crash是统一出现在loop()的case
                    Looper.loop();//主线程的异常会从这里抛出
                } catch (Throwable e) {
                    LoggerUtils.e("【全局异常补货】",e);
                }
            }
        });
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        try {
            LoggerUtils.e("【全局异常补货】",throwable);
            throwable.printStackTrace();
            thread.interrupt();
        } catch (Exception ignored) {

        }
    }
}
