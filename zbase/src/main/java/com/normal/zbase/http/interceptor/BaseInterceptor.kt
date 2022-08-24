package com.normal.zbase.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by zsf on 2022/8/24 13:34
 * ******************************************
 * *
 * ******************************************
 */
open class BaseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}