package com.normal.zbase.http.client

import androidx.lifecycle.LifecycleOwner
import com.google.gson.GsonBuilder
import com.normal.zbase.BuildConfig
import com.normal.zbase.http.bean.BaseBean
import com.normal.zbase.http.interceptor.HeaderInterceptor
import com.normal.zbase.http.utils.NullOnEmptyConverterFactory
import com.normal.zbase.http.utils.Rxlifecycle
import com.normal.zbase.utils.tools.ApplicationUtils
import com.uber.autodispose.FlowableSubscribeProxy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.HashMap
import java.util.concurrent.TimeUnit

/**
 * Created by zsf on 2022/1/17 16:54
 * *******************
 *    网路请求
 * *******************
 */
object ApiManager {
    @JvmStatic
    lateinit var mRetrofit: Retrofit
    lateinit var api:ApiService

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        val cache =
            Cache(File(ApplicationUtils.context().cacheDir, "http_cache"), 1024 * 1024 * 100)
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .cache(cache)
            .cookieJar(JavaNetCookieJar(cookieManager))
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) { //正式环境禁用网络日志
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        val okHttpClient: OkHttpClient = builder.addInterceptor(HeaderInterceptor()).build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
        mRetrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(ApiConfig.HOST_URL)
            .build()
        api = apiService()
    }

    @JvmStatic
      fun apiService(): ApiService {
        return mRetrofit.create(ApiService::class.java)
    }


    @JvmStatic
    fun <T> get(
        flowable:Flowable<T>,
        owner: LifecycleOwner
    ): FlowableSubscribeProxy<T> {
        return flowable
            .compose(RxSchedulers.io_main())
            .`as`(Rxlifecycle.bind(owner));
    }

}
