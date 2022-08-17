package com.normal.zbase.arouter;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ARouterUtils {
    public static void startActivity(String pagePath) {
        ARouter.getInstance().build(pagePath).navigation();
    }

    public static void navigation(String pagePath, String key1, int value1, String key2, int value2) {
        ARouter.getInstance().build(RouterConstants.Path.APP_MAIN)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withInt(key1, value1)
                .withInt(key2, value2)
                .navigation();
    }


}