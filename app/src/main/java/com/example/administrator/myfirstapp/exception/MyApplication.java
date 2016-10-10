package com.example.administrator.myfirstapp.exception;

import android.app.Application;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
