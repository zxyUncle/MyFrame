package com.normal.zbase.http.subject

import android.content.Context
import android.os.UserManager
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.normal.zbase.R
import com.normal.zbase.arouter.RouterConstants
import com.normal.zbase.http.exception.APIException
import com.normal.zbase.utils.hideLoading
import com.normal.zbase.utils.showLoad
import com.normal.zbase.utils.tools.ApplicationUtils
import com.zxy.zxydialog.TToast
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
/**
 * Created by zsf on 2022/8/17 14:58
 * *******************
 *    处理器
 * *******************
 */
abstract class ApiSubscriber<T> @JvmOverloads constructor(
        private val mContext: AppCompatActivity? = null,
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

    private fun accept(t: Throwable): APIException? {
        return try {
            when (t) {
                is APIException -> {
                    t
                }
                is SocketTimeoutException -> {
                    APIException(ApiConfig.CODE_NO_NETWORK, SOCKET_TIME_OUT_EXCEPTION)
                }
                is ConnectException -> {
                    APIException(ApiConfig.CODE_NO_NETWORK, CONNECT_EXCEPTION)
                }
                is UnknownHostException -> {
                    APIException(ApiConfig.CODE_NO_NETWORK, UNKNOWN_HOST_EXCEPTION)
                }
                is HttpException -> {
                    APIException(t.code(), t.message)
                }
                else -> {
                    APIException(ApiConfig.CODE_UNKNOWN, t.message)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            APIException(ApiConfig.CODE_UNKNOWN, e.message)
        }
    }

    protected open fun onResponse(t: T, isSucc: Boolean) {}

    protected open fun onErrorHandle(exception: APIException?) {
        try {
            if (isShowLogin && exception?.isTokenInvalid == true) {
//                UserManager.getInstance().logout();
            } else if (isShowNoNetwork && exception?.isNoNetwork == true) {
                TToast.show(ApplicationUtils.context().resources.getString(R.string.network_no))
//                ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
//                        .withString(RouterConstants.KV.PAGE_PATH, RouterConstants.Path.BASE_NO_NETWORK)
//                        .navigation()
            } else if (mContext != null) {
                exception.let {
                    TToast.show(exception?.message)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onCompleteHandle() {}

    companion object {
        private  val SOCKET_TIME_OUT_EXCEPTION = ApplicationUtils.context().resources.getString(R.string.network_no_message)
        private  val CONNECT_EXCEPTION = ApplicationUtils.context().resources.getString(R.string.network_no_message)
        private  val UNKNOWN_HOST_EXCEPTION = ApplicationUtils.context().resources.getString(R.string.network_no_message)
    }
}