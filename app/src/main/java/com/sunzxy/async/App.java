package com.sunzxy.async;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zhengxiaoyong on 16/3/17.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
