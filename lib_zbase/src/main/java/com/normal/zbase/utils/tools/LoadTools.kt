package com.normal.zbase.utils.tools

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by zxy on 2020/8/6 16:06
 * ******************************************
 * *
 * ******************************************
 */
class LoadTools {
    var kProgressHUD: KProgressHUD? = null

    //zxy 单例模式
    companion object {
        val INSTANCE: LoadTools by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LoadTools()
        }
    }

    fun setContent(message: String) {
        if (kProgressHUD == null) {
            kProgressHUD!!.setLabel(message)
        }
    }

    fun show(activity: AppCompatActivity, message: String) {
        activity.lifecycleScope.launch(Dispatchers.Main) {
            if (kProgressHUD == null) {
                kProgressHUD = KProgressHUD.create(activity)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(message)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0f)
                        .show()
            } else {
                kProgressHUD?.show()
            }
        }
    }

    fun hide() {
        if (kProgressHUD != null) {
            kProgressHUD?.dismiss()
            kProgressHUD = null
        }
    }

}