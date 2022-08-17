package com.normal.zbase.http.build

import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.normal.zbase.http.subject.RxSchedulers
import com.normal.zbase.http.utils.Rxlifecycle
import com.uber.autodispose.FlowableSubscribeProxy
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by zsf on 2022/8/16 19:46
 * ******************************************
 * *
 * ******************************************
 */
abstract class HttpRequestService {
   private var path:String?=""
   private var host:String?=""
   private var params:Map<Any,@JvmSuppressWildcards Any>?= mapOf()

    // 转换的工具
    private val transformer = Gson()

    open fun path(path: String): HttpRequestService {
        this.path = path
        return this
    }

    open fun host(host:String):HttpRequestService{
        this.host = host
        return this
    }

    open fun params(params:Map<Any,@JvmSuppressWildcards Any>?):HttpRequestService{
        this.params = params
        return this
    }

//    fun <R : Any> enqueueByClz(clz: Class<R>): Observable<R> {
//        return enqueueByClz(transformer, transformer.getAdapter(clz))
//    }


    fun <T> execute(
        flowable: Flowable<T>,
        owner: LifecycleOwner
    ): FlowableSubscribeProxy<T> {

        return flowable
            .compose(RxSchedulers.io())
            .`as`(Rxlifecycle.bind(owner));
    }

    fun <T> execute1(
        flowable: Flowable<T>,
        owner: LifecycleOwner
    ): FlowableSubscribeProxy<T> {
        return flowable
            .compose(RxSchedulers.io())
            .`as`(Rxlifecycle.bind(owner));
    }


}