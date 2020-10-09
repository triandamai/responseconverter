package com.triandamai.converter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public abstract class BaseApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    
}
