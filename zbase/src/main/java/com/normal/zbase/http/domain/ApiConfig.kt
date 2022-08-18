package com.normal.zbase.http.domain

import com.normal.zbase.BuildConfig
import com.normal.zbase.http.bean.BaseHostUrlDto

/**
 * *******************
 *    APi配置
 * *******************
 */
object ApiConfig {
    const val CODE_NAME = "code" //成功或者失败的code或者status名字，根据服务器不同，变为不同

    const val CODE_SUCCESS = "0000" //请求成功

    const val CODE_UNKNOWN = "-10000"

    const val CODE_NO_NETWORK = "-10001"

    const val CODE_TOKEN_INVALID = "40001" //token 失效

    private var normalHost: String? = null //默认域名

    /**
     * 设置默认域名，如果未null，就胡使用域名列表中的第一个
     */
    fun setNormalHost(normalHost: String?) {
        this.normalHost = normalHost
    }

    /**
     * 获取主机地址
     * 不传参数：依据默认（根据是否Debug）
     * 传参数：依据key得到值
     */
    @JvmOverloads
    fun getHostUrl(url: String? = normalHost) = url

}