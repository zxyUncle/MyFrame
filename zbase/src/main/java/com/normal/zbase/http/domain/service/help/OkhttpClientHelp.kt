package com.normal.zbase.http.domain.service.help

import com.normal.zbase.BuildConfig
import com.normal.zbase.http.domain.ApiConfig
import com.normal.zbase.utils.obj.LoggerUtils
import com.normal.zbase.utils.tools.ApplicationUtils
import com.normal.zbase.utils.tools.MyHttpLoggingInterceptor
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
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

        var loggingInterceptor = MyHttpLoggingInterceptor { message ->
            if (ApiConfig.isPrintHttpLog) { //是否打印日志
                try {
                    if (ApiConfig.filterLogMsg.size > 0) {
                        ApiConfig.filterLogMsg.map { filterLog ->
                            if (!message.contains(filterLog)) {
                                LoggerUtils.i(message)
                            }
                        }
                    } else {
                        LoggerUtils.i(message)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    LoggerUtils.e(e)
                }
            }
        }.setLevel(MyHttpLoggingInterceptor.Level.BODY)
            .setListFilter(ApiConfig.filterUrlLogList)
        //增加自定义拦截器
        ApiConfig.interceptorList.map { interceptor ->
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }
}