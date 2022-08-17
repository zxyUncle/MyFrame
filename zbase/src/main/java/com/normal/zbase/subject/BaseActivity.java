package com.normal.zbase.subject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleObserver;

import com.alibaba.android.arouter.launcher.ARouter;
import com.normal.zbase.R;
import com.normal.zbase.databinding.ToolbarLayoutBinding;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.http.subject.ApiManager;
import com.normal.zbase.http.subject.ApiHttp;
import com.normal.zbase.manager.ActivityStackManager;
import com.normal.zbase.event.EventBusUtils;
import com.normal.zbase.utils.tools.ApplicationUtils;

/**
 * Created by zsf on 2022/1/20 11:39
 * *******************
 * BaseActivity
 * *******************
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements
        View.OnClickListener {
    protected T mDataBind;
    protected ToolbarLayoutBinding toolbarDataBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getActivityManager().addActivity(this);
        init();
        int resID = getLayoutResID();
        if (resID != -1 && resID != 0) {
            mDataBind = DataBindingUtil.setContentView(this, resID);
        }
        if (this instanceof LifecycleObserver) getLifecycle().addObserver((LifecycleObserver) this);
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
        ARouter.init(ApplicationUtils.context());
        ARouter.getInstance().inject(this);
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        ActivityStackManager.getActivityManager().removeActivity(this);
        if (mDataBind != null) mDataBind.unbind();
    }

    @Override
    public void onClick(View v) {
    }

    public BaseActivity<T> getActivity() {
        return this;
    }

    public T getBinding() {
        return mDataBind;
    }

    /**
     * 获取布局ID
     */
    protected abstract int getLayoutResID();

    /**
     * 初始化view
     */
    protected void initView(Bundle savedInstanceState) {
        getToolbarLayoutBinding();
    }

    protected void init() {
    }

    protected ApiHttp apiService() {
        return ApiManager.apiService();
    }

    protected void setToolbarTitle(String title) {
        if (toolbarDataBind != null) {
            toolbarDataBind.title.setText(title);
        }
    }

    protected ToolbarLayoutBinding getToolbarLayoutBinding() {
        try {
            toolbarDataBind = DataBindingUtil.bind(mDataBind.getRoot().findViewById(R.id.toolbar));
            toolbarDataBind.left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            return toolbarDataBind;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return null;
        }
    }

}