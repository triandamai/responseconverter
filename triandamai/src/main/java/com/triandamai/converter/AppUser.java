package com.triandamai.converter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class AppUser {
    private static String KEY_CURRENTUSER = "zzzzabx";
    private static AppUser appUser;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    @SuppressLint("CommitPrefEdits")
    public AppUser(Context context){
        String KEY_PERSISTANCE = "zzabxx";
        sharedPreferences  = context.getSharedPreferences(KEY_PERSISTANCE,0);
        editor = sharedPreferences.edit();
    }
    public static void initialize(Context context){
        if(appUser == null){
            appUser = new AppUser(context);
        }
    }
    public static AppUser getInstance(){
        return appUser;
    }
    public <T> T getCurrentUser(Class<T> tClass){
        Gson gson = new Gson();
        String data = sharedPreferences.getString(KEY_CURRENTUSER,"");
        T o= gson.fromJson(data,tClass);
        return ((T)o);
    }
    public void setCurrentuser(Object data,Class tClass){
        Gson gson = new Gson();
        editor.putString(KEY_CURRENTUSER,gson.toJson(tClass.cast(data)));
        editor.apply();
    }
    public <T> T getData(String KEY,Class<T> tClass){
        Gson gson = new Gson();
        String data = sharedPreferences.getString(KEY,"");
        T o= gson.fromJson(data,tClass);
        return ((T)o);
    }
    public void setData(String KEY,Object data,Class tClass){
        Gson gson = new Gson();
        editor.putString(KEY,gson.toJson(tClass.cast(data)));
        editor.apply();
    }
}
