package com.normal.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.normal.main.databinding.ActivityMainBinding;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.MessageEventBean;
import com.normal.zbase.http.bean.LoginResultBean;
import com.normal.zbase.http.subject.ApiConfig;
import com.normal.zbase.http.subject.ApiFoctory;
import com.normal.zbase.http.subject.ApiSubscriber;
import com.normal.zbase.subject.BaseActivity;
import com.normal.zbase.subject.BaseRecyclerViewAdapter;

import org.greenrobot.eventbus.Subscribe;

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
@BindEventBus
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
                /**  可选项  start **/
                .host(ApiConfig.INSTANCE.getHostUrl("139")) //可选 重置HostUrl
                .bindLifecycleOwner(this)   //可选 绑定指定的Actiivt，不填默认绑定最上层activity生命周期
                .isForm(false)              //可选，是否是表单提交，默认false
                /**  可选项   end **/
                .body(map)
                .execute(LoginResultBean.class)
                .subscribe(new ApiSubscriber<LoginResultBean>(getActivity()) {
                    @Override
                    protected void onSuccess(LoginResultBean loginResultBean) {
                        super.onSuccess(loginResultBean);
                        Log.e("zxy",loginResultBean.getCode());
                    }

                    @Override
                    protected void onFail(LoginResultBean loginResultBean) {
                        super.onFail(loginResultBean);
                        Log.e("zxy",loginResultBean.getCode());
                    }
                });
    }

    @Subscribe
    public void onEvnet(MessageEventBean eventBean){

    }
}