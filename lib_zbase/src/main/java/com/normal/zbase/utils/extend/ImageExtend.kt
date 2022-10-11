@file:JvmName("ImageExtend")
@file:JvmMultifileClass

package com.normal.zbase.utils.extend

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.normal.zbase.R
import com.normal.zbase.utils.tools.ApplicationUtils

/**
 * Created by zxy on 2020/7/22 13:58
 * ******************************************
 * * 图片加载扩展函数
 * ******************************************
 */
fun ImageView.loadUrl(img: String = "") {
    var requestOptions = RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
        .signature(ObjectKey(System.currentTimeMillis()));//通过签名的改变刷新
    Glide.with(ApplicationUtils.context()).load(img).apply(requestOptions).into(this)
}