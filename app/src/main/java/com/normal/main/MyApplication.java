package com.normal.main;


import com.normal.zbase.subject.BaseApplication;

public class MyApplication extends BaseApplication {
    @Override
    public String getHostUrlList() {
        return "http://10.10.12.186/";
    }

    @Override
    protected void initConfig() {
        super.initConfig();
    }
}
