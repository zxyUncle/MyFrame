package com.normal.zbase.http.bean

/**
 * Created by zsf on 2022/8/16 13:36
 * ******************************************
 * * 可扩展的主机类
 *   可以加渠道，店铺等信息
 * ******************************************
 */
data class BaseHostUrlDto(
    var hostUrl: String? = "", //主机域名
    var channel: String? = "",//渠道
    var storeId: String? = "",//商店Id
    var ext: String? = "",//扩展字段
)