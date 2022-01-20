package com.normal.zbase.http.client

import com.normal.zbase.http.bean.BaseBean
import com.normal.zbase.http.bean.LoginResultBean
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by zsf on 2022/1/17 17:10
 * ******************************************
 * * 接口
 * ******************************************
 */
public interface ApiService {
    //账号密码登录
    @POST("cloudpick/rest/admin/api/v1/logon/account/user/login")
    fun accountLogin(@Body arrayMap: Map<String, String>): Flowable<LoginResultBean>
}