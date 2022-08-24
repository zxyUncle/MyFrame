package com.normal.main.http

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by zsf on 2022/1/19 17:02
 * ******************************************
 * * 示例拦截器
 * ******************************************
 */
class TestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}