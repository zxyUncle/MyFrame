package com.normal.zbase.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.HashMap

/**
 * Created by zsf on 2022/1/19 17:02
 * ******************************************
 * * 请求头
 * ******************************************
 */
class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}