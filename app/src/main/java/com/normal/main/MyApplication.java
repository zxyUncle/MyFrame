package com.normal.main;


import com.normal.main.http.TestInterceptor;
import com.normal.zbase.http.domain.ApiConfig;
import com.normal.zbase.subject.BaseApplication;

import java.util.Arrays;

public class MyApplication extends BaseApplication {
    @Override
    protected void initConfig() {
        super.initConfig();
        //初始化域名,这个东西可以放到其他页面，只要在网络请求之前就行
        ApiConfig.setNormalHost("http://10.10.12.186/");
        //初始化默认的拦截器，自定义拦截日志等，可以省略
//        ApiConfig.setInterceptorList(Arrays.asList(new TestInterceptor(),new TestInterceptor()));
    }
}
