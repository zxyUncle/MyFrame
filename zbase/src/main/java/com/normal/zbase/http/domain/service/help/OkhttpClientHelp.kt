package com.normal.zbase.http.domain.service.help

import com.normal.zbase.BuildConfig
import com.normal.zbase.http.domain.ApiConfig
import com.normal.zbase.utils.obj.Logger
import com.normal.zbase.utils.tools.ApplicationUtils
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

/**
 * Created by zsf on 2022/8/17 10:38
 * ******************************************
 * *
 * ******************************************
 */
object OkhttpClientHelp {

    fun getClient(): OkHttpClient {
        val cache =
                Cache(File(ApplicationUtils.context().cacheDir, "http_cache"), 1024 * 1024 * 100)
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .cache(cache)
                .cookieJar(JavaNetCookieJar(cookieManager))
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
        //增加自定义拦截器
        ApiConfig.getInterceptorList().map { interceptor->
            builder.addInterceptor(interceptor)
        }
        if (BuildConfig.DEBUG) { //正式环境禁用网络日志
            var loggingInterceptor = HttpLoggingInterceptor {
                if (BuildConfig.DEBUG) {
                    try {
                        Logger.i(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Logger.e(e)
                    }
                }
            }.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }
}