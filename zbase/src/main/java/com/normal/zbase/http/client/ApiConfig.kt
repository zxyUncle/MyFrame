package com.normal.zbase.http.client

import com.normal.zbase.BuildConfig

/**
 * Created by zsf on 2022/1/17 16:54
 * *******************
 *    APi配置
 * *******************
 */
object ApiConfig {
    @JvmField
    val HOST_URL: String = getHostUrl()

    @JvmField
    val CODE_NAME = "code" //成功或者失败的code或者status名字，根据服务器不同，变为不同

    @JvmField
    val CODE_SUCCESS = "0000" //请求成功

    @JvmField
    val CODE_UNKNOWN = "-10000"

    @JvmField
    val CODE_NO_NETWORK = "-10001"

    @JvmField
    val CODE_TOKEN_INVALID = "40001"


    private fun getHostUrl(): String {
        if (BuildConfig.DEBUG)
            return "http://10.10.12.186/"
        else
            return "http://10.10.12.111/";

    }
}