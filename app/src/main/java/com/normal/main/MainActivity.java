package com.normal.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.normal.main.databinding.ActivityMainBinding;
import com.normal.zbase.http.bean.LoginResultBean;
import com.normal.zbase.http.client.ApiManager;
import com.normal.zbase.http.client.ApiSubscriber;
import com.normal.zbase.http.bean.BaseBean;
import com.normal.zbase.http.client.RxSchedulers;
import com.normal.zbase.http.exception.APIException;
import com.normal.zbase.http.utils.Rxlifecycle;
import com.normal.zbase.subject.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zsf on 2022/1/17 17:38
 * *******************
 * 主入口
 * *******************
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mDataBind.setPage(this);
    }
    public void onLogin(View view) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", "11111111112");
        map.put("password", "111111");
        map.put("channel", "wzsz");
        map.put("system", "customer");
        map.put("expiry", "-1");
        ApiManager.get(apiService().accountLogin(map), this)
                .subscribe(new ApiSubscriber<LoginResultBean>() {
                    @Override
                    protected void onResponse(LoginResultBean baseBean, boolean isSucc) {
                        super.onResponse(baseBean, isSucc);
                        Log.e("zxy", isSucc + "");
                    }

                    @Override
                    protected void onErrorHandle(@Nullable APIException exception) {
                        super.onErrorHandle(exception);
                    }
                });

    }
}