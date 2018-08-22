package com.example.camera;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能

    }

    public static Application getApplication() {
        return application;
    }
}
