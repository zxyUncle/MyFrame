package com.normal.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.normal.zbase.http.bean.LoginResultBean
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
        lifecycleScope.launch {

            val list: List<LoginResultBean> = ArrayList()
            list.asFlow().collect { Collectors.toMap(Function { obj: LoginResultBean -> obj.userId }, Function.identity(),
                    BinaryOperator { key1: LoginResultBean, key2: LoginResultBean? -> key1 })
            }

        }
    }
}