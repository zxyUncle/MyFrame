package com.normal.zbase.http.domain

import okhttp3.Interceptor

/**
 * *******************
 *    APi配置
 * *******************
 */
object ApiConfig {
    @JvmStatic
    var CODE_NAME = "code" //成功或者失败的code或者status名字，根据服务器不同，变为不同
    @JvmStatic
    var CODE_SUCCESS = "0000" //请求成功
    @JvmStatic
    var CODE_UNKNOWN = "-10000"//未知
    @JvmStatic
    var CODE_NO_NETWORK = "-10001"//网路异常
    @JvmStatic
    var CODE_TOKEN_INVALID = "40001" //token 失效

    @JvmStatic
    var HTTP_TAG = "HTTP" //默认日志的TAG

    @JvmStatic
    var isPrintHttpLog = true //是否输出日志

    @JvmStatic
    var normalHost: String? = null //设置默认域名，可以动态更换，或者通过网路请求指定host更换

    @JvmStatic
    var interceptorList: MutableList<Interceptor> = mutableListOf()  //用户自定义的拦截器

    @JvmStatic
    var filterUrlLogList: MutableList<String> = mutableListOf()  //不输出指定的Url路径的日志

    @JvmStatic
    var filterLogMsg: MutableList<String> = mutableListOf()  //不输出指定的糟乱的日志(内容) 部分相

}