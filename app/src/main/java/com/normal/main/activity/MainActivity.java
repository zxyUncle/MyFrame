package com.normal.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.normal.main.R;
import com.normal.main.databinding.ActivityMainBinding;
import com.normal.main.http.HttpUrl;
import com.normal.main.http.bean.ChannelStatusInfoDto;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.MessageEventBean;
import com.normal.zbase.http.bean.LoginResultDto;
import com.normal.zbase.http.domain.ApiConfig;
import com.normal.zbase.http.domain.ApiFoctory;
import com.normal.zbase.http.domain.ApiSubscriber;
import com.normal.zbase.manager.ActivityStackManager;
import com.normal.zbase.subject.BaseActivity;
import com.normal.zbase.subject.BaseRecyclerViewAdapter;
import com.normal.zbase.utils.obj.LoggerUtils;

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
    public static MainActivity instance;
    private BaseRecyclerViewAdapter<String> adapter = new BaseRecyclerViewAdapter<>(R.layout.adapter_item);
    private List list = Arrays.asList("o1", "o2");

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        instance = this;
//        setToolbarTitle("Title");
        mDataBind.setMPage(this);
        adapter.setNewInstance(list);
    }

    public void onPost(View view) {
        String[] a = null;
        Log.e("zxy",a[0]);
//        int a=  0/0; //全局异常拦截
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "11111111112");
        map.put("password", "111111");
        map.put("channel", "wzsz");
        map.put("system", "customer");
        map.put("expiry", "-1");
        ApiFoctory
                .post(HttpUrl.login)
                /**  可选项  start **/
                //可选 重置HostUrl
                .host("http://10.10.101.39")
                //可选 绑定指定的Actiivt，不填默绑定最上层栈的activity，置空不绑定生命周期
                .bindLifecycleOwner(ActivityStackManager.getActivityManager().currentActivity())
                //可选，是否是表单提交，默认false
                .isForm(false)
                /**  可选项   end **/
                .body(map)
                .execute(LoginResultDto.class)
                .subscribe(new ApiSubscriber<LoginResultDto>() {
                    @Override
                    protected void onSuccess(LoginResultDto loginResultDto) {
                        super.onSuccess(loginResultDto);
                        LoggerUtils.json(loginResultDto);
                    }

                    @Override
                    protected void onFail(LoginResultDto loginResultDto) {
                        super.onFail(loginResultDto);
                        LoggerUtils.json(loginResultDto);
                    }
                });
    }

    public void onGet(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderChannel", "nale");
        ApiFoctory.get(HttpUrl.select_channel_status)
                .params(map)
                .execute(ChannelStatusInfoDto.class)
                .subscribe(new ApiSubscriber<ChannelStatusInfoDto>() {
                    @Override
                    protected void onSuccess(ChannelStatusInfoDto channelStatusInfoDto) {
                        super.onSuccess(channelStatusInfoDto);
                    }

                    @Override
                    protected void onFail(ChannelStatusInfoDto channelStatusInfoDto) {
                        super.onFail(channelStatusInfoDto);
                    }
                });
    }

    public void  onStartAct(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity2.class);
        startActivity(intent);
    }


    @Subscribe
    public void onEvnet(MessageEventBean eventBean){

    }
}