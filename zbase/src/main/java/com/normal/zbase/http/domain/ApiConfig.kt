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

    //静态设置HOST
    private var HOST_LIST = mutableMapOf<String,BaseHostUrlDto>()
    /**
     * 获取主机地址
     * 方法多态，可传可不传参数
     * 不传参数：依据默认（根据是否Debug）
     * 传参数：依据key得到值
     */
    @JvmOverloads
    fun getHostUrl(key: String? = null): String {
        return if (key != null) {
            HOST_LIST[key]?.hostUrl ?: ""
        } else {
             HOST_LIST.map {
                return it.value.hostUrl?:""
            }
            return ""
        }
    }

    /**
     * 增加主机值
     */
    fun addHostList(key: String, value: BaseHostUrlDto) {
        if (!HOST_LIST.containsKey(key)) {
            HOST_LIST[key] = value
        }
    }

    /**
     * 只增加主机值
     */
    fun addHostList(hostMap:LinkedHashMap<String,BaseHostUrlDto>) {
        hostMap.map {
            addHostList(it.key,it.value)
        }
    }


}