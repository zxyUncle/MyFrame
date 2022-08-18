package com.normal.main;


import com.normal.zbase.http.bean.BaseHostUrlDto;
import com.normal.zbase.http.domain.ApiConfig;
import com.normal.zbase.subject.BaseApplication;

import java.util.LinkedHashMap;

public class MyApplication extends BaseApplication {
   @Override
   protected void initConfig() {
      super.initConfig();

      //初始化域名,这个东西可以放到其他页面，只要在网络请求之前就行
      ApiConfig.INSTANCE.setNormalHost("http://10.10.12.186/");
   }
}
