@file:JvmName("LaunchExtend")
@file:JvmMultifileClass
package com.normal.zbase.utils.extend

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

/**
 * Created by zsf on 2021/2/7 16:12
 * ******************************************
 * * 协程扩展类
 * ******************************************
 */
//Kotlin 协程
//implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1"
//implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"

//"lifecycleScope"               : 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1',
//"viewModelScope"               : 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1',

/**
 * 闭包使用：launchIO({异步},{挂起函数同步主线程},{异常返回，可以省略})
 *
 * @param block 异步协程，{可以实现delay()、repeat()、async()、await()、suspend()等}
 * @param callback 同步主协程，view更新
 * @param error 异常返回
 * @return 可操作协程 job.cancelAndJoin()
 */
fun <T> AppCompatActivity.launchIOToMain(
    block:  suspend CoroutineScope.() -> T,
    callback:(T) -> Unit,
    error: ((Exception) -> Unit) = {}
): Job {
    return lifecycleScope.launch {
        try {
            val data = withContext(Dispatchers.IO) { //协程切换，得到IO协程的泛型结果
                block()
            }
            withContext(Dispatchers.Main) {//协程切换主协程
                callback(data)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {//异常
                error.invoke(e)
            }
        }
    }
}

/**
 * 同步主线程View的更新
 */
fun AppCompatActivity.launchMain(block: () -> Unit):Job {
    return lifecycleScope.launch {
        withContext(Dispatchers.Main) {
            block()
        }
    }
}

fun AppCompatActivity.launchIO(block: () -> Unit) :Job {
    return lifecycleScope.launch {
        withContext(Dispatchers.IO) {
            block()
        }
    }
}

fun AppCompatActivity.launchDefault(block: () -> Unit):Job {
    return lifecycleScope.launch {
        withContext(Dispatchers.Default) {
            block()
        }
    }
}

