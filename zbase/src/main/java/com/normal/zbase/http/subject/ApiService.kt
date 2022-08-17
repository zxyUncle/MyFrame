package com.normal.zbase.http.subject

import com.normal.zbase.http.bean.LoginResultBean
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by zsf on 2022/1/17 17:10
 * ******************************************
 * * 接口
 * ******************************************
 */
public interface ApiService {
    //账号密码登录
    @POST("cloudpick/rest/admin/api/v1/logon/account/user/login")
    fun accountLogin(@Body arrayMap: Map<String, String>): Flowable<LoginResultBean>

    /**
     * GET请求
     *
     * @param headers 从我个人的角度上来说，是不希望增加这个Header的,请求头信息
     * @param path 路径地址
     * @param params url的encode请求参数
     */
    @GET("{path}")
    fun get(
        @HeaderMap headers: Map<String, String>,
        @Path(value = "path", encoded = true) path: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

    /**
     * get请求的外沿封装
     *
     * @param url 完整的请求链接
     * @param params queries相关的参数
     */
    @GET
    fun get(
        @Url url: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

    /**
     * POST请求
     * 参数通过Body的方式进行传递
     * 注意body传递的形式content-type默认定位为'text/json'
     *
     * @param headers 请求头信息
     * @param path 路径地址
     * @param params url的encode请求参数
     * @param body 请求的数据集合
     */
    @POST("{path}")
    fun post(
        @HeaderMap headers: Map<String, String>,
        @Path(value = "path", encoded = true) path: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

    /**
     * post请求中参数是以string的形式透传的情况
     *
     * @param headers 请求头信息
     * @param path 路径地址
     * @param params url的encode请求参数
     * @param body 数据结构体
     */
    @POST("{path}")
    fun post(
        @HeaderMap headers: Map<String, String>,
        @Path(value = "path", encoded = true) path: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
        @Body body: Any
    ): Flowable<ResponseBody>

    /**
     * post请求参数确认为url的完整链接的情况
     *
     * @param url 完整的请求链接(注意内部会有，Uri.parse()的逻辑check)
     * @param params query请求的附带参数集合
     * @param body 请求的body体，支持数据类型的透传
     */
    @POST
    fun post(
        @Url url: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
        @Body body: Any
    ): Flowable<ResponseBody>

    /**
     * post请求参数确认为url的完整链接的情况
     *
     * @param url 完整的请求链接
     * @param params query请求的附带参数集合
     * @param body 具体的请求map集合
     */
    @POST
    fun post(
        @Url url: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

    /**
     * POST请求
     * 参数通过Form表单的形式传递
     * TODO Form模式下支持对于URl的请求,个人觉得没必要
     *
     * @param headers 请求头信息
     * @param path 路径地址
     * @param params url的encode请求参数
     * @param forms 传递的参数
     */
    @FormUrlEncoded
    @POST("{path}")
    fun formPost(
        @HeaderMap headers: Map<String, String>,
        @Path(value = "path", encoded = true) path: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
        @FieldMap forms: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

    /**
     * PUT请求
     * 注意body传递的形式content-type默认定位为'text/json'
     *
     * @param headers 请求头信息
     * @param path 路径地址
     * @param params url的encode请求参数
     * @param body 请求的数据集合
     */
    @PUT("{path}")
    fun put(
        @HeaderMap headers: Map<String, String>,
        @Path("path") path: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

    /**
     * PUT请求
     * 参数通过Form表单的形式传递
     *
     * @param headers 请求头信息
     * @param path 路径地址
     * @param params url的encode请求参数
     * @param forms 传递的参数
     */
    @FormUrlEncoded
    @PUT("{path}")
    fun formPut(
        @HeaderMap headers: Map<String, String>,
        @Path("path") path: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>,
        @Body forms: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

    /**
     * DELETE请求
     *
     * @param headers 请求头信息
     * @param path 路径地址
     * @param params url的encode请求参数
     */
    @DELETE("{path}")
    fun delete(
        @HeaderMap headers: Map<String, String>,
        @Path(value = "path", encoded = true) path: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards Any>
    ): Flowable<ResponseBody>

}