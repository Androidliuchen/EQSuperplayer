package com.eq.EQSuperPlayer.activity;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.eq.EQSuperPlayer.custom.IHandler;
import com.eq.EQSuperPlayer.custom.UIHandler;

/**
 * Created by Administrator on 2017/1/3.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected static UIHandler handler = new UIHandler(Looper.getMainLooper()); // activity
    // 获取系统自动创建的loop


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initData();
        setHandler();
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void handler(Message msg);

    private void setHandler() {
        handler.setHandler(new IHandler() {
            @Override
            public void handleMessage(Message msg) {
                handler(msg);
            }
        });
    }

}
