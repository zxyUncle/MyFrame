package com.normal.main;


import com.normal.zbase.http.bean.BaseHostUrlDto;
import com.normal.zbase.http.domain.ApiConfig;
import com.normal.zbase.subject.BaseApplication;

import java.util.LinkedHashMap;

public class MyApplication extends BaseApplication {
   @Override
   protected void initConfig() {
      super.initConfig();

      //初始化域名，第一个是默认域名
      ApiConfig.INSTANCE.addHostList(new LinkedHashMap<String,BaseHostUrlDto>() {
         {
            put("139", new BaseHostUrlDto("http://10.10.10.139/", "渠道ID", "商店ID", "扩展字段"));
            put("186", new BaseHostUrlDto("http://10.10.12.186/", "渠道ID", "商店ID", "扩展字段"));
         }
      });

   }
}
