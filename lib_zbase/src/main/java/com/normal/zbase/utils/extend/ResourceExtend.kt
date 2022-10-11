@file:JvmName("ResourceExtend")
@file:JvmMultifileClass
package com.normal.zbase.utils

import android.os.Build
import android.text.Html
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.normal.zbase.utils.tools.ApplicationUtils

/**
 * Created by zsf on 2021/1/11 14:47
 * ******************************************
 * * 资源扩展
 * ******************************************
 */
fun stringRes(@StringRes stringRes: Int) = ApplicationUtils.context().getString(stringRes)

fun drawableRes(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(ApplicationUtils.context(), drawableRes)

@Suppress("DEPRECATION")
fun String.renderHtml(): String =
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        Html.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    else Html.fromHtml(this).toString()