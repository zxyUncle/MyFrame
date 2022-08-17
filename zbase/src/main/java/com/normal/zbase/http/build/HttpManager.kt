package com.normal.zbase.http.build

import android.util.Log
import com.google.gson.GsonBuilder
import com.normal.zbase.BuildConfig
import com.normal.zbase.http.interceptor.BaseUrlInterceptor
import com.normal.zbase.http.interceptor.HeaderInterceptor
import com.normal.zbase.http.subject.ApiConfig
import com.normal.zbase.http.subject.ApiManager
import com.normal.zbase.http.utils.NullOnEmptyConverterFactory
import com.normal.zbase.utils.tools.ApplicationUtils
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

/**
 * Created by zsf on 2022/8/17 9:49
 * ******************************************
 * *
 * ******************************************
 */
public class HttpManager {
    private fun initRetrofit() {
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
        if (BuildConfig.DEBUG) { //正式环境禁用网络日志
            var loggingInterceptor = HttpLoggingInterceptor {
                if (BuildConfig.DEBUG) {
                    try {
                        Log.e("HTTP", it)
                    } catch (e: Exception) {
                        e.printStackTrace();
                        Log.e("HTTP", it)
                    }
                }
            }.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        builder.addInterceptor(BaseUrlInterceptor())
        builder.addInterceptor(HeaderInterceptor())
        val okHttpClient: OkHttpClient = builder.build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
        ApiManager.mRetrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(ApiConfig.getHostUrl())
            .build()
        ApiManager.api = ApiManager.apiService()
    }
}