package com.normal.zbase.utils.tools

import android.content.Context
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

    fun setContent(message:String){
        if (kProgressHUD == null) {
            kProgressHUD!!.setLabel(message)
        }
    }

    fun show(mContext: Context, message: String) {
        GlobalScope.launch(Dispatchers.Main) {
            if (kProgressHUD == null) {
                kProgressHUD = KProgressHUD.create(mContext)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.1f)
                    .show()
            } else {
                kProgressHUD?.show()
            }
        }
    }

    fun hide() {
        GlobalScope.launch(Dispatchers.Main) {
            if (kProgressHUD != null) {
                kProgressHUD?.dismiss()
                kProgressHUD = null
            }
        }
    }


}