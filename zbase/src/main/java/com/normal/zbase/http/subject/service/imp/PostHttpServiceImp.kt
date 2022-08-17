package com.normal.zbase.http.subject.service.imp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.gson.reflect.TypeToken
import com.normal.zbase.http.subject.ApiService
import com.normal.zbase.http.subject.RxSchedulers
import com.normal.zbase.http.subject.service.HttpRequestService
import com.normal.zbase.http.utils.Rxlifecycle
import com.normal.zbase.manager.ActivityStackManager
import com.uber.autodispose.FlowableSubscribeProxy
import io.reactivex.Flowable
import okhttp3.ResponseBody

/**
 * Created by zsf on 2022/8/16 19:44
 * ******************************************
 * *
 * ******************************************
 */
class PostHttpServiceImp(path: String) : HttpRequestService(path) {
    private var body: Map<String, @JvmSuppressWildcards Any> = mapOf()

    /**
     * 更换主机域名
     * http://10.10.10.10 -> http://10.10.10.11
     */
    fun host(host: String): PostHttpServiceImp {
        super.host = host
        return this
    }

    /**
     * 不设置默认使用栈中最上层activity，也可以指定activity
     * 绑定activiyt的生命周期
     * @param AppCompatActivity
     */
    fun bindLifecycleOwner(bindLifecycleOwner: LifecycleOwner): PostHttpServiceImp {
        super.bindLifecycleOwner = bindLifecycleOwner
        return this
    }

    /**
     * 请求参数
     */
    fun params(params: Map<String, @JvmSuppressWildcards Any>?): PostHttpServiceImp {
        super.params = params
        return this
    }

    /**
     * body
     */
    fun body(body: Map<String, @JvmSuppressWildcards Any>): PostHttpServiceImp {
        this.body = body
        return this
    }

    /**
     * 基类包括
     */
    fun <R> execute(response: Class<R>): FlowableSubscribeProxy<R> {
        return bindFlow(mRetrofit.create(ApiService::class.java).post(path!!, params!!, body!!), response)
    }

    fun <R> execute(response: TypeToken<R>): FlowableSubscribeProxy<R> {
        return bindFlow(mRetrofit.create(ApiService::class.java).post(path!!, params!!, body!!), response)
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