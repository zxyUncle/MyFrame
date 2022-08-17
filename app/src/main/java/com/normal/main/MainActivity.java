package com.normal.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.normal.main.databinding.ActivityMainBinding;
import com.normal.zbase.http.bean.BaseResult;
import com.normal.zbase.http.bean.LoginResultBean;
import com.normal.zbase.http.subject.ApiConfig;
import com.normal.zbase.http.subject.ApiFoctory;
import com.normal.zbase.http.subject.ApiManager;
import com.normal.zbase.http.subject.ApiSubscriber;
import com.normal.zbase.http.exception.APIException;
import com.normal.zbase.subject.BaseActivity;
import com.normal.zbase.subject.BaseRecyclerViewAdapter;

import org.reactivestreams.Subscriber;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zsf on 2022/1/17 17:38
 * *******************
 * 主入口
 * *******************
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private BaseRecyclerViewAdapter<String> adapter = new BaseRecyclerViewAdapter<>(R.layout.adapter_item);
    private List list = Arrays.asList("o1", "o2");

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
//        setToolbarTitle("Title");
        mDataBind.setPage(this);
        adapter.setNewInstance(list);
    }

    public void onLogin(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "11111111112");
        map.put("password", "111111");
        map.put("channel", "wzsz");
        map.put("system", "customer");
        map.put("expiry", "-1");

        ApiFoctory
                .post(HttpUrl.login)
                .host(ApiConfig.INSTANCE.getHostUrl("139")) //可选
                .bindLifecycleOwner(this)   //可选
                .body(map)
                .execute(LoginResultBean.class)
                .subscribe(new ApiSubscriber<LoginResultBean>(getActivity()) {
                    @Override
                    protected void onResponse(LoginResultBean loginResultBean, boolean isSucc) {
                        super.onResponse(loginResultBean, isSucc);
                    }

                    @Override
                    protected void onErrorHandle(@Nullable APIException exception) {
                        super.onErrorHandle(exception);
                    }
                });

    }
}