package com.normal.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.normal.zbase.utils.obj.TimerManager;

import java.text.SimpleDateFormat;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TimerManager timerManager = new TimerManager();

        Button tvContent = findViewById(R.id.tvContent);
        findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerManager.stop();
            }
        });
    }


}