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

    public static Fragment getFragmentByPath(String pagePath) {
        return getFragmentByPath(pagePath, null, null, 0);
    }

    public static Fragment getFragmentByPath(String pagePath, String key, Object value) {
        return getFragmentByPath(pagePath, null, key, value);
    }

    public static Fragment getFragmentByPath(String pagePath, Bundle bundle) {
        return getFragmentByPath(pagePath, bundle, null, null);
    }

    private static Fragment getFragmentByPath(String pagePath, Bundle bundle, String key, Object value) {
        if (!TextUtils.isEmpty(pagePath)) {
            Postcard postcard = ARouter.getInstance().build(pagePath);
            if (bundle != null) {
                postcard.with(bundle);
            }
            if (!TextUtils.isEmpty(key)) {
                if (value instanceof Integer) {
                    postcard.withInt(key, (Integer) value);
                } else if (value instanceof String) {
                    postcard.withString(key, (String) value);
                } else if (value instanceof Parcelable) {
                    postcard.withParcelable(key, (Parcelable) value);
                } else {
                    postcard.withObject(key, value);
                }
            }
            AtomicReference<Object> atomicReference = new AtomicReference<>(postcard.navigation());
            if (postcard.getExtra() == RouterConstants.INTERCEPT_CODE_LOGIN ) {
                atomicReference.set(ARouter.getInstance().build(RouterConstants.Path.MY_LOGIN)
                        .navigation());
            }
            if (atomicReference.get() instanceof Fragment) {
                return (Fragment) atomicReference.get();
            }
        }
        return (Fragment) ARouter.getInstance().build(RouterConstants.Path.BASE_NO_PATH)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withBoolean(RouterConstants.KV.PAGE_HIDE_TOOLBAR, bundle == null)
                .navigation();
    }

    public static void navigation(String pagePath) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .navigation();
    }

    public static void navigation(String pagePath, String key, int value) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withInt(key, value)
                .navigation();
    }

    public static void navigation(String pagePath, String key, String value) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withString(key, value)
                .navigation();
    }

    public static void navigation(String pagePath, String key, Parcelable value) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withParcelable(key, value)
                .navigation();
    }

    public static void navigation(String pagePath, String key1, ArrayList<String> value1, String key2, int value2) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withStringArrayList(key1, value1)
                .withInt(key2, value2)
                .navigation();
    }

    public static void navigation(String pagePath, String key1, String value1, String key2, String value2) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withString(key1, value1)
                .withString(key2, value2)
                .navigation();
    }

    public static void navigation(String pagePath, String key1, int value1, String key2, String value2) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withInt(key1, value1)
                .withString(key2, value2)
                .navigation();
    }

    public static void navigation(String pagePath, String key1, String value1, String key2, int value2) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withString(key1, value1)
                .withInt(key2, value2)
                .navigation();
    }

    public static void navigation(String pagePath, String key1, int value1, String key2, int value2) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .withString(RouterConstants.KV.PAGE_PATH, pagePath)
                .withInt(key1, value1)
                .withInt(key2, value2)
                .navigation();
    }

    public static void navigation(Bundle bundle) {
        ARouter.getInstance().build(RouterConstants.Path.BASE_CONTAINER)
                .with(bundle)
                .navigation();
    }

}