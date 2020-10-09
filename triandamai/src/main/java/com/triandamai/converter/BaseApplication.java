package com.triandamai.converter;

import android.app.Application;
import android.content.Context;

abstract class BaseApplication extends Application {
    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
