package com.normal.main.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.normal.main.R
import com.normal.zbase.http.bean.LoginResultDto
import com.normal.zbase.manager.ActivityStackManager
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.function.BinaryOperator
import java.util.function.Function
import java.util.stream.Collectors

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        ActivityStackManager.getActivityManager().addActivity(this)
        lifecycleScope.launch {

            val list: List<LoginResultDto> = ArrayList()
            list.asFlow().collect { Collectors.toMap(Function { obj: LoginResultDto -> obj.userId }, Function.identity(),
                    BinaryOperator { key1: LoginResultDto, key2: LoginResultDto? -> key1 })
            }

        }
    }

    fun onStartAct(view: View?) {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }

}