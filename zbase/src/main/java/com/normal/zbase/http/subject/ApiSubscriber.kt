package com.normal.zbase.http.subject

import android.content.Context
import com.normal.zbase.http.exception.APIException
import com.normal.zbase.utils.hideLoading
import com.normal.zbase.utils.showLoad
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class ApiSubscriber<T> @JvmOverloads constructor(
    private val mContext: Context? = null,
    isShowDialog: Boolean = false, //    private DialogHelper mDialogHelper;
    private val isShowLogin: Boolean = false,
    private val isShowNoNetwork: Boolean = false
) : ResourceSubscriber<T>() {

    override fun onStart() {
        super.onStart()
        try {
            mContext?.showLoad()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onNext(it: T) {
        val obj = it!!::class.java
        val fieldCode = obj.getDeclaredField(ApiConfig.CODE_NAME)
        fieldCode.isAccessible = true
        val code = fieldCode.get(it)
        try {
            when (code) {
                ApiConfig.CODE_SUCCESS ->//成功
                    onResponse(it, true)
                else -> {// 失败
                    onResponse(it, false)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onError(t: Throwable) {
        try {
            hideLoading()
            onErrorHandle(accept(t))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onComplete() {
        try {
            hideLoading()
            onCompleteHandle();
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun accept(t: Throwable): APIException? {
        return try {
            if (t is APIException) {
                t as APIException
            } else if (t is SocketTimeoutException) {
                APIException(ApiConfig.CODE_NO_NETWORK, SOCKET_TIME_OUT_EXCEPTION)
            } else if (t is ConnectException) {
                APIException(ApiConfig.CODE_NO_NETWORK, CONNECT_EXCEPTION)
            } else if (t is UnknownHostException) {
                APIException(ApiConfig.CODE_NO_NETWORK, UNKNOWN_HOST_EXCEPTION)
            } else if (t is HttpException) {
                APIException((t as HttpException).code(), t.message)
            } else {
                APIException(ApiConfig.CODE_UNKNOWN, t.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            APIException(ApiConfig.CODE_UNKNOWN, e.message)
        }
    }

    protected open fun onResponse(t: T, isSucc: Boolean) {}
    protected open fun onErrorHandle(exception: APIException?) {
        try {
//            if (isShowLogin && exception.isTokenInvalid()) {
//                UserManager.getInstance().logout();
//            } else if (isShowNoNetwork && exception.isNoNetwork()) {
//                ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
//                        .withString(RouterConstants.KV.PAGE_PATH, RouterConstants.Path.BASE_NO_NETWORK)
//                        .navigation();
//            } else if (mContext != null) {
//                ToastUtil.show(mContext, exception.getMessage());
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun onCompleteHandle() {}

    companion object {
        private const val SOCKET_TIME_OUT_EXCEPTION = "网络连接超时\n请检查您的网络状态"
        private const val CONNECT_EXCEPTION = "网络连接异常\n请检查您的网络状态"
        private const val UNKNOWN_HOST_EXCEPTION = "网络异常\n请检查您的网络状态"
    }

    init {
        if (isShowDialog && mContext != null) {
        }
    }
}