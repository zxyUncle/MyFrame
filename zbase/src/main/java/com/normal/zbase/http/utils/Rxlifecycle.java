package com.normal.zbase.http.utils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.ScopeProvider;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public class Rxlifecycle {

    /**
     * @param owner
     * @param untilEvent 指定解绑的生命周期
     * @return
     */
    public static <T> AutoDisposeConverter<T> bind(LifecycleOwner owner, Lifecycle.Event untilEvent) {
        if (owner!=null){
            return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, untilEvent));
        }else{
            return AutoDispose.autoDisposable(ScopeProvider.UNBOUND);
        }

    }
}
