package com.normal.zbase.http.subject

import com.normal.zbase.http.bean.LoginResultBean
import io.reactivex.Flowable
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