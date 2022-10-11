package com.normal.main;


import com.normal.zbase.subject.BaseApplication;

public class MyApplication extends BaseApplication {
    @Override
    public String getHostUrlList() {
        return "http://10.10.12.133:888/";
    }

    @Override
    public String getHTTP_TAG() {
        return "HTTP";
    }

    @Override
    public boolean getIsPrintHttpLog() {
        return true;
    }

    @Override
    protected void initConfig() {
        super.initConfig();
        
    }
}
