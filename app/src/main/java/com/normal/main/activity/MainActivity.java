package com.normal.main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.normal.main.R;
import com.normal.main.databinding.ActivityMainBinding;
import com.normal.main.http.HttpApi;
import com.normal.main.http.dto.ChannelStatusInfoDto;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.MessageEventBean;
import com.normal.zbase.http.dto.LoginResultDto;
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
        toolbarTitle("Title");//标题
        toolbarHindLeftBack();//隐藏返回键
        mDataBind.setActivity(this);
        //adapter
        mDataBind.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBind.mRecyclerView.setAdapter(adapter);
        adapter.setNewInstance(list);
        //获取权限
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

    /**
     * POSt请求示例
     * @param view
     */
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
                .post(HttpApi.login)
                /**  可选项  start **/
                //可选 重置HostUrl
                .host("http://10.10.101.39")
                //可选 绑定指定的Actiivt，不填默绑定最上层栈的activity，置空不绑定生命周期，绑定了默认跟随activiti销毁而销毁
                .bindLifecycleOwner(ActivityStackManager.getActivityManager().currentActivity())
                //可选，是否是表单提交，默认false
                .isForm(false)
                /**  可选项   end **/
                .body(map)
                .execute(LoginResultDto.class)//可以使用父类的code（使用继承关系），也可以使用子类的code
                .subscribe(new ApiSubscriber<LoginResultDto>(true) {//显示加载动画
                    @Override
                    protected void onSuccess(LoginResultDto loginResultDto) {//非必实现，成功的返回（code == 200），修改code 在ApiConfig文件中
                        super.onSuccess(loginResultDto);
                        TToast.show(new Gson().toJson(loginResultDto));
                        LoggerUtils.json(loginResultDto);
                    }

                    @Override
                    protected void onFail(LoginResultDto loginResultDto) {//非必实现，，非200的请求结果
                        super.onFail(loginResultDto);
                        TToast.show(new Gson().toJson(loginResultDto));
                        LoggerUtils.json(loginResultDto);
                    }

                    @Override
                    protected void onCompleteRequest() {//非必实现，，整个请求完成的结果
                        super.onCompleteRequest();
                        //可以做刷新控件的结束动画
                    }
                });
    }

    /**
     * Get请求示例
     * @param view
     */
    public void onGet(View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderChannel", "nale");
        ApiFoctory.get(HttpApi.select_channel_status)
                .params(map)
                .execute(ChannelStatusInfoDto.class)
                .subscribe(new ApiSubscriber<ChannelStatusInfoDto>() {
                    @Override
                    protected void onSuccess(ChannelStatusInfoDto channelStatusInfoDto) {
                        super.onSuccess(channelStatusInfoDto);
                    }
                });
    }


    /**
     * 事件
     * @param eventBean
     */
    @Subscribe
    public void onEvnet(MessageEventBean eventBean) {

    }
}