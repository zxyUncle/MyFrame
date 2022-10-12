package com.normal.zbase.http.domain.service.help

import com.normal.zbase.http.domain.ApiConfig
import com.normal.zbase.logs.Logs
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
    lateinit var builderBuild: OkHttpClient

    fun getClient(): OkHttpClient {
        if (::builderBuild.isInitialized){
            return builderBuild
        }
        val cache =
            Cache(File(ApplicationUtils.context().cacheDir, "http_cache"), 1024 * 1024 * 100)
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        var builder:OkHttpClient.Builder = OkHttpClient.Builder()
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
                                Logs.API.i(message)
                            }
                        }
                    } else {
                        Logs.API.i(message)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Logs.API.e(e)
                }
            }
        }.setLevel(MyHttpLoggingInterceptor.Level.BODY)
            .setListFilter(ApiConfig.filterUrlLogList)
        //增加自定义拦截器
        ApiConfig.interceptorList.map { interceptor ->
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor(loggingInterceptor)
        builderBuild = builder.build()
        return builderBuild
    }
}