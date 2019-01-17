package com.jikexueyuan.ui.uioptimization;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/15 0015.
 */
public class MyApplication extends Application {

    public static final String TAG = "uioptimization";

    public MyApplication() {
        Log.d(TAG, "MyApplication()");
    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(TAG, "attachBaseContext()");
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");

        //开启子线程或后台服务做一些初始化操作

    }
}
