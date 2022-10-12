package com.normal.main.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.normal.main.R;
import com.normal.main.databinding.ActivityMainBinding;
import com.normal.main.http.HttpApi;
import com.normal.main.http.dto.ChannelStatusInfoDto;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.MessageEventBean;
import com.normal.zbase.http.domain.ApiFoctory;
import com.normal.zbase.http.domain.ApiSubscriber;
import com.normal.zbase.http.dto.LoginResultDto;
import com.normal.zbase.logs.Logs;
import com.normal.zbase.manager.ActivityStackManager;
import com.normal.zbase.subject.BaseActivity;
import com.normal.zbase.subject.BaseRecyclerViewAdapter;
import com.zxy.zxydialog.TToast;

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
        Log.e("zxy","开始权限请求");
        List permissList = Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    public void permissionRequest1() {
        List permissList = Arrays.asList(Manifest.permission.CAMERA);
        //请求权限

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

//                    @Override
//                    protected void onSuccess(LoginResultDto loginResultDto) {//非必实现，成功的返回（code == 200），code 或 200在ApiConfig文件中修改
//                        super.onSuccess(loginResultDto);
//                        TToast.show(new Gson().toJson(loginResultDto));
//                        LoggerUtils.json(loginResultDto);
//                    }
//
//                    @Override
//                    protected void onFail(LoginResultDto loginResultDto) {//非必实现，，非200的请求结果
//                        super.onFail(loginResultDto);
//                        TToast.show(new Gson().toJson(loginResultDto));
//                        LoggerUtils.json(loginResultDto);
//                    }

                    @Override
                    protected void onCompleteHandler() {//非必实现，整个请求完成的回调，方便做刷新动画的结束标志等
                        super.onCompleteHandler();
                    }


                    @Override
                    protected void onResponseHandler(LoginResultDto loginResultDto) {//非必实现，不判断成功还是失败，直觉返回，跟onSuccess、onFail互斥，二选一
                        super.onResponseHandler(loginResultDto);
                        TToast.show(new Gson().toJson(loginResultDto));
                        Logs.NORMAL.json(loginResultDto);
                        if (loginResultDto.isSuccess()){

                        }else{

                        }
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
                    protected void onSuccess(ChannelStatusInfoDto channelStatusInfoDto) {//非必实现，成功的返回（code == 200），code 或 200在ApiConfig文件中修改
                        super.onSuccess(channelStatusInfoDto);
                        Logs.NORMAL.json(channelStatusInfoDto);
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