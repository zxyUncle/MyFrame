package com.normal.zbase.http.subject.service.imp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.gson.reflect.TypeToken
import com.normal.zbase.http.subject.ApiHttp
import com.normal.zbase.http.subject.RxSchedulers
import com.normal.zbase.http.subject.service.HttpRequestService
import com.normal.zbase.http.utils.Rxlifecycle
import com.uber.autodispose.FlowableSubscribeProxy
import io.reactivex.Flowable
import okhttp3.ResponseBody

/**
 * Created by zsf on 2022/8/16 19:44
 * ******************************************
 * * Post请求
 * ******************************************
 */
class PostHttpServiceImp(path: String) : HttpRequestService(path) {
    private lateinit var body: Any
    private var isForm: Boolean = false //是否是表单请求

    /**
     * 更换主机域名
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
     * 不设置默认使用栈中最上层activity，也可以指定activity
     * 绑定activiyt的生命周期
     * @param AppCompatActivity
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

    /**
     * body Any
     */
    fun body(body: Any): PostHttpServiceImp {
        this.body = body
        return this
    }

    /**
     * body Any
     */
    fun isForm(isForm: Boolean): PostHttpServiceImp {
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
            bindFlow(mRetrofit.create(ApiHttp::class.java).post(headers, path!!, params!!, body!!), response)
        }
    }

    /**
     * 没有父类的基类，基类本身包含code message
     */
    fun <R> execute(response: TypeToken<R>): FlowableSubscribeProxy<R> {
        return bindFlow(mRetrofit.create(ApiHttp::class.java).post(headers, path!!, params!!, body!!), response)
    }

    /**
     * 继承BaseReslut父类的执行器，基类本身不包含code message
     */
//    fun <R> executeBase(response: Class<R>): FlowableSubscribeProxy<BaseResult<R>> {
//        val from:BaseResult<R> = gson.from<BaseResult<R>>("")
//        gson.fromJson<BaseResult<R>>(BaseBean::class.java)
//        val type = object : TypeToken<BaseResult<R?>?>() {}.type
//        var a = BaseResult<R>
//
//        return bindFlowBase(mRetrofit.create(ApiHttp::class.java).post(headers, path!!, params!!, body!!), response)
//    }


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