package com.normal.zbase.http.domain.service.imp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.gson.reflect.TypeToken
import com.normal.zbase.http.domain.ApiHttp
import com.normal.zbase.http.domain.RxSchedulers
import com.normal.zbase.http.domain.service.HttpRequestService
import com.normal.zbase.http.utils.Rxlifecycle
import com.normal.zbase.manager.ActivityStackManager
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Flowable
import kotlinx.coroutines.*
import okhttp3.ResponseBody

/**
 * Created by zsf on 2022/8/16 19:44
 * ******************************************
 * * Put请求
 * ******************************************
 */
class PutHttpServiceImp(path: String) : HttpRequestService(path) {
    private var body: Map<String, @JvmSuppressWildcards Any> = mapOf()
    private var isForm: Boolean = false //是否是表单请求

    /**
     * 更换主机域名
     * http://10.10.10.10 -> http://10.10.10.11
     */
    override fun host(host: String): PutHttpServiceImp {
        super.host = host
        return this
    }

    /**
     * 请求头
     */
    override fun headers(headers: Map<String, Any>): PutHttpServiceImp {
        super.headers = headers
        return this
    }

    /**
     * 1、不设置默认使用栈中最上层activity，
     * 2、也可以指定activity，
     * 3、置空就是不跟随activity生命周期
     * 绑定activiyt的生命周期
     * @param AppCompatActivity
     */
    override fun bindLifecycleOwner(bindLifecycleOwner: LifecycleOwner): PutHttpServiceImp {
        super.bindLifecycleOwner = bindLifecycleOwner
        return this
    }

    /**
     * 请求参数
     */
    override fun params(params: Map<String, @JvmSuppressWildcards Any>?): PutHttpServiceImp {
        super.params = params
        return this
    }

    /**
     * body Any
     */
    fun body(body: Map<String, @JvmSuppressWildcards Any>): PutHttpServiceImp {
        this.body = body
        return this
    }

    /**
     * 是否是表单提交
     */
    fun isForm(isForm: Boolean): PutHttpServiceImp {
        this.isForm = isForm
        return this
    }

    /**
     * 没有父类的基类，基类本身包含code message
     */
    fun <R> execute(response: Class<R>): FlowableSubscribeProxy<R> {
        return if (isForm) {
            bindFlow(mRetrofit.create(ApiHttp::class.java).formPost(headers, path!!, params!!, body!! as Map<String, @JvmSuppressWildcards Any>), response)
        } else {
            bindFlow(mRetrofit.create(ApiHttp::class.java).put(headers, path!!, params!!, body!!), response)
        }
    }

    /**
     * 没有父类的基类，基类本身包含code message
     */
    fun <R> execute(response: TypeToken<R>): FlowableSubscribeProxy<R> {
        return bindFlow(mRetrofit.create(ApiHttp::class.java).put(headers, path!!, params!!, body!!), response)
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