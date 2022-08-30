package com.normal.zbase.http.domain

import androidx.appcompat.app.AppCompatActivity
import com.normal.zbase.R
import com.normal.zbase.http.exception.APIException
import com.normal.zbase.manager.ActivityStackManager
import com.normal.zbase.utils.hideLoading
import com.normal.zbase.utils.obj.LoggerUtils
import com.normal.zbase.utils.showLoad
import com.normal.zbase.utils.tools.ApplicationUtils
import com.zxy.zxydialog.TToast
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException
import java.lang.reflect.Field
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by zsf on 2022/8/17 14:58
 * *******************
 *    处理器
 * *******************
 */
/**
 * @param mContext 当前Activity的上线文  可选  不传默认使用最上层的activity栈
 * @param isShowDialog 是否显示加载动画  可选
 * @param isShowLogin Token失效了，是否跳转到登录页  可选
 */
abstract class ApiSubscriber<T> @JvmOverloads constructor(
        private val isShowLoad: Boolean = false,
        private val mContext: AppCompatActivity = ActivityStackManager.getActivityManager().currentActivity(),
        private val isShowLogin: Boolean = false,
) : ResourceSubscriber<T>() {

    override fun onStart() {
        super.onStart()
        try {
            if (isShowLoad)
                mContext.showLoad()
        } catch (e: Exception) {
            LoggerUtils.e(e)
            e.printStackTrace()
        }
    }

    override fun onNext(it: T) {
        try {
            val obj = it!!::class.java
            //获取父类的属性
            var fieldCode = getDeclaredField(it, ApiConfig.CODE_NAME)
            if (fieldCode == null) {
                //获取子类的属性
                fieldCode = obj.getDeclaredField(ApiConfig.CODE_NAME)
            }
            fieldCode?.isAccessible = true
            when (fieldCode?.get(it)) {
                ApiConfig.CODE_SUCCESS ->//成功
                    onSuccess(it)
                ApiConfig.CODE_TOKEN_INVALID -> {//token失效
                    onFail(it)
//                    ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
//                        .withString(RouterConstants.KV.PAGE_PATH, RouterConstants.Path.BASE_NO_NETWORK)
//                        .navigation()
                }
                else -> {// 失败
                    onFail(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoggerUtils.e(e)
        }
    }

    override fun onError(t: Throwable) {
        try {
            hideLoading()
            val exception = accept(t)
            onErrorHandle(exception)
            onCompleteRequest()
        } catch (e: Exception) {
            e.printStackTrace()
            LoggerUtils.e(e)
        }
    }

    override fun onComplete() {
        try {
            hideLoading()
            onCompleteRequest()
        } catch (e: Exception) {
            e.printStackTrace()
            LoggerUtils.e(e)
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
                    APIException(ApiConfig.CODE_NO_NETWORK, SOCKET_TIME_OUT_EXCEPTION)
//                    APIException(t.code(), t.message)
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

    /**
     * 非必实现，成功的返回（code == 200），修改code 在ApiConfig文件中
     * @param t 返回的对象
     */
    protected open fun onSuccess(t: T) {}

    /**
     * 非必实现，失败 code ！= 200
     * @param t 返回的对象
     */
    protected open fun onFail(t: T) {}

    /**
     * 非必实现，完成整个http请求，方便做刷新动画的结束标志等
     */
    protected open fun onCompleteRequest() {}
    /**
     * 错误，非200 的返回
     */
    protected open fun onErrorHandle(exception: APIException?) {
        try {
            exception.let {
                TToast.show(exception?.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoggerUtils.e(e)
        }
    }


    companion object {
        private val SOCKET_TIME_OUT_EXCEPTION = ApplicationUtils.context().resources.getString(R.string.network_no_message)
        private val CONNECT_EXCEPTION = ApplicationUtils.context().resources.getString(R.string.network_no_message)
        private val UNKNOWN_HOST_EXCEPTION = ApplicationUtils.context().resources.getString(R.string.network_no_message)
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    open fun getDeclaredField(`object`: Any, fieldName: String?): Field? {
        var field: Field? = null
        var clazz: Class<*> = `object`.javaClass
        while (clazz != Any::class.java) {
            try {
                field = clazz.getDeclaredField(fieldName)
                return field
            } catch (e: Exception) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
            clazz = clazz.superclass
        }
        return null
    }
}