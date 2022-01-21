package com.normal.zbase.subject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;

import com.alibaba.android.arouter.launcher.ARouter;
import com.normal.zbase.R;
import com.normal.zbase.databinding.ToolbarLayoutBinding;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.EventBusUtils;
import com.normal.zbase.http.client.ApiManager;
import com.normal.zbase.http.client.ApiService;

/**
 * Created by zsf on 2022/1/20 11:49
 * *******************
 *    BaseFragment
 * *******************
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment implements View.OnClickListener {

    protected T mDataBind;

    protected AppCompatActivity mActivity;

    protected boolean isHidden;

    private boolean isPageShow = true;//页面是否显示

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (mDataBind == null) {
            mDataBind = DataBindingUtil.inflate(inflater, getLayoutResID(), container, false);
            ARouter.getInstance().inject(this);
            initToolbar(mDataBind.getRoot());
            initView(mDataBind.getRoot(), savedInstanceState);
            post(this::loadData);
            if (this instanceof LifecycleObserver)
                getLifecycle().addObserver((LifecycleObserver) this);
            if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
                EventBusUtils.register(this);
            }
        }
        return mDataBind.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!isHidden && isPageShow) {
            isPageShow = false;
            onPagePause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden && !isPageShow) {
            isPageShow = true;
            onPageResume();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.isHidden = hidden;
        if (mActivity != null) {
            if (!isHidden) {
                if (!isPageShow) {
                    isPageShow = true;
                    onPageResume();
                }
            } else {
                if (isPageShow) {
                    isPageShow = false;
                    onPagePause();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        if (null != mDataBind) {//提示should remove parent view
            ViewParent parent = mDataBind.getRoot().getParent();
            if (parent != null) ((ViewGroup) parent).removeView(mDataBind.getRoot());
        }
    }

    @Override
    public void onClick(View v) {
    }

    public T getBinding() {
        return mDataBind;
    }

    protected void post(Runnable action) {
        mDataBind.getRoot().post(action);
    }

    protected void postDelayed(Runnable action, long delayMillis) {
        mDataBind.getRoot().postDelayed(action, delayMillis);
    }

    protected abstract int getLayoutResID();

    protected void initToolbar(View view) {
    }

    protected void initView(View view, Bundle savedInstanceState) {
    }

    protected void loadData() {
    }

    public boolean isPageShow() {
        return isPageShow;
    }

    public void onPageResume() {
    }

    public void onPagePause() {
    }


    protected ToolbarLayoutBinding getToolbarLayoutBinding(View view) {
        return DataBindingUtil.bind(view.findViewById(R.id.custom_toolbar));
    }

    protected ApiService apiService(){
        return ApiManager.apiService();
    }
}