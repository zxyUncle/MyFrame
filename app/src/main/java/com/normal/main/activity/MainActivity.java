package com.normal.main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.normal.main.R;
import com.normal.main.databinding.ActivityMainBinding;
import com.normal.main.http.HttpUrl;
import com.normal.main.http.bean.ChannelStatusInfoDto;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.MessageEventBean;
import com.normal.zbase.http.bean.LoginResultDto;
import com.normal.zbase.http.domain.ApiFoctory;
import com.normal.zbase.http.domain.ApiSubscriber;
import com.normal.zbase.manager.ActivityStackManager;
import com.normal.zbase.manager.PermissionManager;
import com.normal.zbase.subject.BaseActivity;
import com.normal.zbase.subject.BaseRecyclerViewAdapter;
import com.normal.zbase.utils.obj.LoggerUtils;
import com.zxy.zxydialog.TToast;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by zsf on 2022/1/17 17:38
 * *******************
 * 主入口
 * *******************
 */
@BindEventBus
public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private BaseRecyclerViewAdapter<String> adapter = new BaseRecyclerViewAdapter<>(R.layout.adapter_item);
    private List list = Arrays.asList("o1", "o2", "o2");

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        toolbarTitle("Title");
        toolbarHindLeftBack();
        mDataBind.setActivity(this);
        mDataBind.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBind.mRecyclerView.setAdapter(adapter);
        adapter.setNewInstance(list);

        permissionRequest();
    }

    /**
     * 权限请求
     */
    public void permissionRequest() {
        List permissList = Arrays.asList(
                PermissionManager.getREAD_EXTERNAL_STORAGE(),
                PermissionManager.getWRITE_EXTERNAL_STORAGE());
        //请求权限
        PermissionManager.reqeustPermission(this, permissList, () -> {
            TToast.show("已经全部同意");
            return null;
        }, (Function1<List<String>, Unit>) list -> {
            TToast.show("不同意的权限有" + new Gson().toJson(list));
            return null;
        });
    }

    public void onPost(View view) {
//        String[] a = null;
//        Log.e("zxy",a[0]);//全局异常拦截

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
                .host("http://10.10.10.139")
                //可选 绑定指定的Actiivt，不填默绑定最上层栈的activity，置空不绑定生命周期，绑定了默认跟随activiti销毁而销毁
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
                        TToast.show(new Gson().toJson(loginResultDto));
                        LoggerUtils.json(loginResultDto);
                    }

                    @Override
                    protected void onFail(LoginResultDto loginResultDto) {
                        super.onFail(loginResultDto);
                        TToast.show(new Gson().toJson(loginResultDto));
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


    @Subscribe
    public void onEvnet(MessageEventBean eventBean) {
        //事件
    }
}