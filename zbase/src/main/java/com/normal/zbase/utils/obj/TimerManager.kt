package com.normal.zbase.utils.obj

import kotlinx.coroutines.*

class TimerManager {
     var job:Job?=null

    fun start(callback: () -> Unit) {
        job = GlobalScope.launch {
            while (true) {
                delay(1)
                withContext(Dispatchers.Main) {
                    callback()
                }
            }
        }

    }

    fun stop(){
        if (job!=null){
            job?.cancel()
        }
    }


}