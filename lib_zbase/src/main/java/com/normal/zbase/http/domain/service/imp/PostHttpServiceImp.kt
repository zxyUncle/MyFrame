package com.normal.zbase.http.domain.service.imp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.gson.reflect.TypeToken
import com.normal.zbase.http.domain.ApiHttp
import com.normal.zbase.http.domain.service.HttpRequestService
import com.normal.zbase.http.domain.service.help.RxSchedulers
import com.normal.zbase.http.utils.Rxlifecycle
import com.uber.autodispose.FlowableSubscribeProxy
import io.reactivex.Flowable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File

/**
 * Created by zsf on 2022/8/16 19:44
 * ******************************************
 * * Post请求
 * ******************************************
 */
class PostHttpServiceImp(path: String) : HttpRequestService(path) {
    private var body: Any = Any()
    private var isForm: Boolean = false //是否是表单请求
    private var files: HashMap<String, RequestBody> = hashMapOf()

    /**
     * 更换主机域名  或者更换代理地址 host:80
     * http://10.10.10.10 -> http://10.10.10.11
     */
    override fun host(host: String): PostHttpServiceImp {
        super.host = host
        return this
    }

    /**
     * 请求头
     */
    override fun headers(headers: Map<String, Any>): PostHttpServiceImp {
        super.headers = headers
        return this
    }

    /**
     * 1、不设置默认使用栈中最上层activity，
     * 2、也可以指定activity，
     * 3、置空就是不跟随activity生命周期
     * 绑定activiyt的生命周期
     * @param bindLifecycleOwner 当前Activity Context
     */
    override fun bindLifecycleOwner(bindLifecycleOwner: LifecycleOwner): PostHttpServiceImp {
        super.bindLifecycleOwner = bindLifecycleOwner
        return this
    }

    /**
     * 请求参数
     */
    override fun params(params: Map<String, @JvmSuppressWildcards Any>?): PostHttpServiceImp {
        super.params = params
        return this
    }

    //文件参数
    fun files(fileParams: Map<String, File>): PostHttpServiceImp {
        for (map in fileParams.entries) {
            val body = map.value.asRequestBody("multipart/form-data;charset=UTF-8".toMediaTypeOrNull())
            files[map.key] = body
        }
        return this
    }

    /**
     * body Any
     */
    fun body(body: Any): PostHttpServiceImp {
        this.body = body
        return this
    }

    /**
     * 是否是表单提交
     */
    fun isForm(isForm: Boolean): PostHttpServiceImp {
        this.isForm = isForm
        return this
    }

    /**
     *
     */
    fun <R> execute(response: Class<R>): FlowableSubscribeProxy<R> {
        return if (isForm) {
            bindFlow(
                mRetrofit.create(ApiHttp::class.java)
                    .formPost(headers, path!!, params!!, body as Map<String, @JvmSuppressWildcards Any>), response
            )
        } else {
            bindFlow(mRetrofit.create(ApiHttp::class.java).post(headers, path!!, params!!,files, body), response)
        }
    }

    /**
     * 没有父类的基类，基类本身包含code message
     */
    fun <R> execute(response: TypeToken<R>): FlowableSubscribeProxy<R> {
        return bindFlow(mRetrofit.create(ApiHttp::class.java).post(headers, path!!, params!!, files, body), response)
    }


    private fun <R> bindFlow(flowable: Flowable<ResponseBody>, response: TypeToken<R>): FlowableSubscribeProxy<R> {
        return flowable.map(JsonConverterMap(gson, gson.getAdapter(response)))
            .compose(RxSchedulers.io_main())
            .`as`(Rxlifecycle.bind(bindLifecycleOwner, Lifecycle.Event.ON_DESTROY))
    }

    private fun <R> bindFlow(flowable: Flowable<ResponseBody>, response: Class<R>): FlowableSubscribeProxy<R> {
        return flowable.map(JsonConverterMap(gson, gson.getAdapter(response)))
            .compose(RxSchedulers.io_main())
            .`as`(Rxlifecycle.bind(bindLifecycleOwner, Lifecycle.Event.ON_DESTROY))
    }


}