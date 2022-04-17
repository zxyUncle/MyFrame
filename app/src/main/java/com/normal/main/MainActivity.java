package com.normal.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.normal.main.databinding.ActivityMainBinding;
import com.normal.zbase.http.bean.LoginResultBean;
import com.normal.zbase.http.subject.ApiManager;
import com.normal.zbase.http.subject.ApiSubscriber;
import com.normal.zbase.http.exception.APIException;
import com.normal.zbase.subject.BaseActivity;
import com.normal.zbase.subject.BaseRecyclerViewAdapter;

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
    private List list = Arrays.asList("o1","o2");
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setToolbarTitle("Title");
        mDataBind.setPage(this);
        adapter.setNewInstance(list);
    }

    public void onLogin(View view) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", "11111111112");
        map.put("password", "111111");
        map.put("channel", "wzsz");
        map.put("system", "customer");
        map.put("expiry", "-1");
        ApiManager.execute(apiService().accountLogin(map), getActivity())
                .subscribe(new ApiSubscriber<LoginResultBean>(getActivity(),true) {
                    @Override
                    protected void onResponse(LoginResultBean baseBean, boolean isSucc) {
                        super.onResponse(baseBean, isSucc);
                    }

                    @Override
                    protected void onErrorHandle(@Nullable APIException exception) {
                        super.onErrorHandle(exception);
                    }
                });

    }
}