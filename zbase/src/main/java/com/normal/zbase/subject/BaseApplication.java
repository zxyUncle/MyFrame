package com.normal.zbase.subject;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.normal.zbase.BuildConfig;
import com.normal.zbase.exceptions.CrashHandler;
import com.normal.zbase.http.domain.ApiConfig;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

public abstract class BaseApplication extends Application {
    private static BaseApplication mApp;
    private long mStartActivityTime = 0;

    /**
     * 指定的主机host
     * @return
     */
    public abstract String getHostUrlList();
    /**
     * 指定的自定义拦截器
     * @return
     */
    public  List<Interceptor> getInterceptorList(){return  new ArrayList<>();}
    /**
     * 是否打印Http日志
     * @return
     */
    public  boolean getIsPrintHttpLog(){return true;}
    /**
     * 日志的TAG
     * @return
     */
    public  String getHTTP_TAG(){return ApiConfig.getHTTP_TAG();}

    /**
     * 不输出指定的Url路径的日志 不能包含，一般用来过滤心跳日志，比较拉的后端,一般不用
     * @return
     */
    public  List<String> getFilterUrlLogList(){return new ArrayList<>();}
    /**
     * 不输出指定的糟乱的日志(内容) 部分相 ，一般用来过滤息屏的socket失败日志，一般不用
     * @return
     */
    public  List<String> getFilterLogMsg(){return new ArrayList<>();}


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initARouter();
        initConfig();
        init();
    }


    private void init(){
        ApiConfig.setNormalHost(getHostUrlList());
        ApiConfig.setInterceptorList(getInterceptorList());
        ApiConfig.setPrintHttpLog(getIsPrintHttpLog());
        ApiConfig.setHTTP_TAG(getHTTP_TAG());
        ApiConfig.setFilterUrlLogList(getFilterUrlLogList());
        ApiConfig.setFilterLogMsg(getFilterLogMsg());
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    protected void initConfig() {
        if (!BuildConfig.DEBUG)
            CrashHandler.getInstance().init();//application 中初始化
    }

    protected void destroySDK() {
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        destroySDK();
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        if (System.currentTimeMillis() - mStartActivityTime > 500) {
            mStartActivityTime = System.currentTimeMillis();
            super.startActivity(intent, options);
        }
    }

    public static BaseApplication getApplication() {
        return mApp;
    }

    protected void initARouter() {
        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }


    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}