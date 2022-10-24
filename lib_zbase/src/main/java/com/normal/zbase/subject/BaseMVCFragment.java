package com.normal.zbase.subject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.EventBusUtils;

/**
 * Created by zsf on 2022/1/20 11:49
 * *******************
 *    BaseFragment
 * *******************
 */
public abstract class BaseMVCFragment extends Fragment implements View.OnClickListener {
    protected AppCompatActivity baseActivity;

    protected boolean isHidden;

    private boolean isPageShow = true;//页面是否显示

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.baseActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return  inflater.inflate(getLayoutResID(),null);
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
        if (baseActivity != null) {
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
    }

    @Override
    public void onClick(View v) {
    }

    protected abstract int getLayoutResID();


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


}