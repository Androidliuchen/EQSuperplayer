package com.eq.EQSuperPlayer.activity;

import android.app.Application;
import android.util.Log;

import com.eq.EQSuperPlayer.utils.LanguageUtil;

/**
 * Created by Administrator on 2017/3/30.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("", LanguageUtil.loadData(this));
        LanguageUtil.setLanguage(this, LanguageUtil.loadData(this));

    }
}
