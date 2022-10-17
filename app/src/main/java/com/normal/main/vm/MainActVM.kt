package com.normal.main.vm

import androidx.lifecycle.MutableLiveData
import com.normal.main.http.HttpApi
import com.normal.main.http.dto.ChannelStatusInfoDto
import com.normal.zbase.http.domain.ApiFoctory
import com.normal.zbase.http.domain.ApiSubscriber
import com.normal.zbase.http.dto.LoginResultDto
import com.normal.zbase.manager.ActivityStackManager
import com.normal.zbase.subject.BaseVM
import java.util.HashMap


/**
 * Created by zsf on 2022/10/17 10:13
 * ******************************************
 * *
 * ******************************************
 */
class MainActVM : BaseVM() {
    var loginResultDtoLD: MutableLiveData<LoginResultDto> = MutableLiveData<LoginResultDto>()
    var channelStatusInfoDtoLD: MutableLiveData<ChannelStatusInfoDto> = MutableLiveData<ChannelStatusInfoDto>()

    /**
     * Get请求示例
     *
     * @param view
     */
    fun onGet() {
        val map: MutableMap<String, Any> = HashMap()
        map["orderChannel"] = "nale"
        ApiFoctory.get(HttpApi.select_channel_status)
            .host("http://10.10.10.139")
            .params(map)
            .execute(ChannelStatusInfoDto::class.java)
            .subscribe(object : ApiSubscriber<ChannelStatusInfoDto>() {
                override fun onSuccessHandler(channelStatusInfoDto: ChannelStatusInfoDto) { //非必实现，成功的返回（code == 200），code 或 200在ApiConfig文件中修改
                    super.onSuccessHandler(channelStatusInfoDto)
                    channelStatusInfoDtoLD.value = channelStatusInfoDto
                }

                override fun onFailHandler(t: ChannelStatusInfoDto) {
                    super.onFailHandler(t)
                }

//*********************以上跟以上两种都行，二选一
//*********************上面那种是自动确定code = 0000的，
//*********************下面这种是不帮解析code = 0000，直接返回原生数据，需要自行判断

//                override fun onResponseHandler(channelStatusInfoDto: ChannelStatusInfoDto) {
//                    super.onResponseHandler(channelStatusInfoDto)
//                    channelStatusInfoDtoLD.value = channelStatusInfoDto
//                }
//
//                override fun onCompleteHandler() {
//                    super.onCompleteHandler()
//                }


            })
    }
    /**
     * POSt请求示例
     * @param view
     */
    fun onPost() {
//        String[] a = null;
//        Log.e("zxy",a[0]);//全局异常拦截
        val map: MutableMap<String, Any> = HashMap()
        map["userName"] = "11111111112"
        map["password"] = "111111"
        map["channel"] = "wzsz"
        map["system"] = "customer"
        map["expiry"] = "-1"
        ApiFoctory
            .post(HttpApi.login)
            /**  可选项  start  */ //可选 重置HostUrl
            .host("http://10.10.10.139") //可选 绑定指定的Actiivt，不填默绑定最上层栈的activity，置空不绑定生命周期，绑定了默认跟随activiti销毁而销毁
            .bindLifecycleOwner(
                ActivityStackManager.getActivityManager().currentActivity()
            ) //可选，是否是表单提交，默认false
            .isForm(false)
            /**  可选项   end  */
            .body(map)
            .execute(LoginResultDto::class.java) //可以使用父类的code（使用继承关系），也可以使用子类的code
            .subscribe(object : ApiSubscriber<LoginResultDto>(true) {
//                override fun onSuccessHandler(loginResultDto: LoginResultDto) {
//                    super.onSuccessHandler(loginResultDto)
//                    loginResultDtoLD.value = loginResultDto
//                }
//
//                override fun onFailHandler(loginResultDto: LoginResultDto) {
//                    super.onFailHandler(loginResultDto)
//                }

//*********************以上跟以上两种都行，二选一
//*********************上面那种是自动确定code = 0000的，
//*********************下面这种是不帮解析code = 0000，直接返回原生数据，需要自行判断

                override fun onCompleteHandler() { //非必实现，整个请求完成的回调，方便做刷新动画的结束标志等
                    super.onCompleteHandler()
                }

                override fun onResponseHandler(loginResultDto: LoginResultDto) { //非必实现，不判断成功还是失败，直觉返回，跟onSuccess、onFail互斥，二选一
                    super.onResponseHandler(loginResultDto)
                    loginResultDtoLD.value = loginResultDto
                }
            })
    }
}