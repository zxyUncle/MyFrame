package com.normal.main.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.normal.main.R
import com.normal.main.http.dto.ChannelStatusInfoDto
import com.normal.main.vm.MainActVM
import com.normal.zbase.http.dto.LoginResultDto
import com.normal.zbase.subject.BaseActivity
import com.zxy.zxydialog.TToast

class MVCActivity : BaseActivity() {
    private lateinit var mainActVM: MainActVM
    override fun getLayoutResID() = R.layout.activity_mvcactivity

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mainActVM = ViewModelProvider(baseActivity)[MainActVM::class.java]


    }

    override fun initListener() {
        super.initListener()
        //onPost的返回值
        mainActVM.loginResultDtoLD.observe(baseActivity, { loginResultDto: LoginResultDto ->
            TToast.show(Gson().toJson(loginResultDto))
        })
        //onGet 的返回值
        mainActVM.channelStatusInfoDtoLD.observe(baseActivity, { channelStatusInfoDto: ChannelStatusInfoDto? ->
            TToast.show(Gson().toJson(channelStatusInfoDto))
        })
    }

    fun onClickView(view: View) {
        when (view.id) {
            R.id.btn_get -> mainActVM.onGet()
            R.id.btn_post -> mainActVM.onPost()
        }
    }

}