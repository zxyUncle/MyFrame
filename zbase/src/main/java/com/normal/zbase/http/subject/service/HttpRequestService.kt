package com.normal.zbase.http.subject.service

import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.normal.zbase.http.subject.ApiConfig
import com.normal.zbase.http.subject.service.help.OkhttpClientHelp
import com.normal.zbase.http.utils.NullOnEmptyConverterFactory
import com.normal.zbase.manager.ActivityStackManager
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by zsf on 2022/8/16 19:46
 * ******************************************
 * *
 * ******************************************
 */
abstract class HttpRequestService {
    protected val mRetrofit: Retrofit by lazy {
        initRetrofit()
    }
    protected var path: String? = "" //去掉HostUrl的接口
    protected var host: String? = ""//HostUrl
    protected var headers: Map<String, @JvmSuppressWildcards Any> = mapOf() //请求头
    protected var params: Map<String, @JvmSuppressWildcards Any>? = mapOf() //请求参数

    //是否要绑定指定的activity，否则就使用当前最上层的activity
    protected var bindLifecycleOwner : LifecycleOwner= ActivityStackManager.getActivityManager().currentActivity()
    // 转换的工具
    protected var gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()

    constructor(path:String) {
        this.path = path
    }

    abstract fun host(host: String): HttpRequestService
    abstract fun headers(headers: Map<String, @JvmSuppressWildcards Any>) :HttpRequestService
    abstract fun bindLifecycleOwner(bindLifecycleOwner: LifecycleOwner): HttpRequestService
    abstract fun params(params: Map<String, @JvmSuppressWildcards Any>?): HttpRequestService


    private fun initRetrofit():Retrofit {
      return  Retrofit.Builder()
            .client(OkhttpClientHelp.getClient())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(
                if (TextUtils.isEmpty(host)) {
                    ApiConfig.getHostUrl()
                } else {
                    host!!
                }
            )
            .build()
    }


    /**
     * 仿造{@see retrofit2.converter.gson.GsonResponseBodyConverter}的实现方式进行gson的数据处理
     */
    class JsonConverterMap<R>(
        private val transformer: Gson,
        private val adapter: TypeAdapter<R>
    ) : Function<ResponseBody, R> {
        override fun apply(body: ResponseBody): R {
            return body.use {
                // 第一步，将response响应转换为json的字符串
                val jsonReader = transformer.newJsonReader(body.charStream())

                // 第二步，通过adapter适配转换器转换成具体的类型
                val ret = adapter.read(jsonReader)
//                if (ret is BaseResponseDTO<*>) {
//                    // 统一的处理服务时间
//                    Timer.instance.sycSvrTime(ret.time)
//                }
                ret
            }
        }
    }


}