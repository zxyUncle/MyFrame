package com.normal.main.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.normal.main.R
import com.normal.zbase.http.bean.LoginResultDto
import com.normal.zbase.manager.ActivityStackManager
import com.normal.zbase.utils.extend.launchIO

class MainActivity2 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        ActivityStackManager.getActivityManager().addActivity(this)
        launchIO {//绑定生命周期的协程
            val list: List<LoginResultDto> = ArrayList()
//            list.asFlow().collect { Collectors.toMap(Function { obj: LoginResultDto -> obj.userId }, Function.identity(),
//                    BinaryOperator { key1: LoginResultDto, key2: LoginResultDto? -> key1 })
//            }
//            val list1: List<LoginResultDto> = java.util.ArrayList()
//            list1.stream().collect(
//                Collectors.toMap(
//                    Function { obj: LoginResultDto -> obj.userId }, Function.identity(),
//                    BinaryOperator { key1: LoginResultDto, key2: LoginResultDto? -> key1 })
//            )
        }
    }

    fun onStartAct(view: View?) {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }
}