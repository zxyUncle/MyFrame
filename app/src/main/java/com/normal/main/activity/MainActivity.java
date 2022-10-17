package com.normal.main.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.normal.main.R;
import com.normal.main.databinding.ActivityMainBinding;
import com.normal.main.vm.MainActVM;
import com.normal.zbase.event.BindEventBus;
import com.normal.zbase.event.MessageEventBean;
import com.normal.zbase.manager.PermissionManager;
import com.normal.zbase.subject.BaseActivity;
import com.normal.zbase.subject.BaseRecyclerViewAdapter;
import com.zxy.zxydialog.TToast;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

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

    private MainActVM mainActVM;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        mainActVM = new ViewModelProvider(this).get(MainActVM.class);
        //获取权限
        permissionRequest();
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
        onResponseDate();
    }

    @Override
    protected void initListener() {
        super.initListener();
        onResponseDate();
    }

    public void onClickView(View view) {
        switch (view.getId()){
            case R.id.btn_get:
                mainActVM.onGet();
                break;
            case R.id.btn_post:
                mainActVM.onPost();
                break;
        }
    }

    /**
     * 数据返回类ViewMode LiveData
     */
    private void onResponseDate() {
        //onPost的返回值
        mainActVM.getLoginResultDtoLD().observe(baseActivity, loginResultDto -> {
            TToast.show(new Gson().toJson(loginResultDto));
            if (loginResultDto.isSuccess()) {

            } else {

            }
        });
        //onGet 的返回值
        mainActVM.getChannelStatusInfoDtoLD().observe(baseActivity, channelStatusInfoDto -> {
            TToast.show(new Gson().toJson(channelStatusInfoDto));
        });
    }

    /**
     * 权限请求
     */
    public void permissionRequest() {
        Log.e("zxy", "开始权限请求");
        List permissList = Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //请求权限
        PermissionManager.reqeustPermission(this, permissList, () -> {
            TToast.show("已经全部同意");
            return null;
        }, list -> {
            TToast.show("不同意的权限有" + new Gson().toJson(list));
            return null;
        });
    }


    /**
     * 事件
     *
     * @param eventBean
     */
    @Subscribe
    public void onEvnet(MessageEventBean eventBean) {

    }
}