package com.normal.zbase.http.interceptor;

import android.text.TextUtils;

import com.normal.zbase.http.subject.ApiConfig;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseUrlInterceptor implements Interceptor {

   @Override
   public Response intercept(Chain chain) throws IOException {
      //获取原始的originalRequest
      Request originalRequest = chain.request();
      //获取当前的url
      HttpUrl oldUrl = originalRequest.url();
      //获取originalRequest的创建者builder
      Request.Builder builder = originalRequest.newBuilder();
      HttpUrl baseURL = HttpUrl.parse(ApiConfig.INSTANCE.getHostUrl("139"));
      HttpUrl newHttpUrl = oldUrl.newBuilder()
              .scheme(baseURL.scheme())//http协议如：http或者https
              .host(baseURL.host())//主机地址
              .port(baseURL.port())//端口
              .build();
      Request newRequest = builder.url(newHttpUrl).build();
      return chain.proceed(newRequest);

//      //获取头信息的集合如：jeapp ,njeapp ,mall
//      List<String> urlnameList = originalRequest.headers("app");
//      if (urlnameList != null && urlnameList.size() > 0) {
//         //删除原有配置中的值,就是namesAndValues集合里的值
//         builder.removeHeader("app");
//         //获取头信息中配置的value,如：manage或者mdffx
//         String urlname = urlnameList.get(0);
//         HttpUrl baseURL = null;
//         //根据头信息中配置的value,来匹配新的base_url地址
//         if (!TextUtils.isEmpty(urlname)) {
//            switch (urlname) {
//               case "one":
//                  baseURL = HttpUrl.parse(ApiConfig.INSTANCE.getHostUrl("130"));
//                  break;
//               case "security":
////                  baseURL = HttpUrl.parse(BuildConfig.BASE_SECURITY_SERVER_URL);
//                  break;
//               default:
//                  break;
//            }
            //重建新的HttpUrl，需要重新设置的url部分
//            HttpUrl newHttpUrl = oldUrl.newBuilder()
//                    .scheme(baseURL.scheme())//http协议如：http或者https
//                    .host(baseURL.host())//主机地址
//                    .port(baseURL.port())//端口
//                    .build();
            //获取处理后的新newRequest
//            Request newRequest = builder.url(newHttpUrl).build();
//            return chain.proceed(newRequest);
//         }
//         return chain.proceed(originalRequest);
//      } else {
//         return chain.proceed(originalRequest);
//      }

   }
}