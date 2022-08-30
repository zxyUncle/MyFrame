package com.normal.main;


import com.normal.zbase.http.domain.ApiConfig;
import com.normal.zbase.subject.BaseApplication;

public class MyApplication extends BaseApplication {
    @Override
    public String getHostUrlList() {
        return "http://10.10.12.133:888/";
    }

    @Override
    public String getHTTP_TAG() {
        return "ZXY";
    }

    @Override
    public boolean getIsPrintHttpLog() {
        return true;
    }

    @Override
    protected void initConfig() {
        super.initConfig();
        ApiConfig.setCODE_NAME("code"); //bean 成功或者失败的code ,默认code
        ApiConfig.setCODE_SUCCESS("0000"); //bean 成功或者失败的code,默认0000
    }
}
