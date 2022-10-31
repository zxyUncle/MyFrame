package com.normal.zbase.subject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import com.alibaba.android.arouter.launcher.ARouter;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.EventBusUtils;
import com.normal.zbase.manager.ActivityStackManager;
import com.normal.zbase.utils.tools.ApplicationUtils;

/**
 * Created by zsf on 2022/1/20 11:39
 * *******************
 * BaseActivity
 * *******************
 */
public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener {
    public AppCompatActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = this;
        setContentView(getLayoutResID());
        ActivityStackManager.getActivityManager().addActivity(this);
        init();
        if (this instanceof LifecycleObserver) getLifecycle().addObserver((LifecycleObserver) this);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
        ARouter.init(ApplicationUtils.context());
        ARouter.getInstance().inject(this);
        initView(savedInstanceState);
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        ActivityStackManager.getActivityManager().removeActivity(this);
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 获取布局ID
     */
    protected abstract int getLayoutResID();

    /**
     * 初始化view
     */
    protected void initView(Bundle savedInstanceState) {

    }

    protected void initListener(){}

    protected void init() {
    }


}