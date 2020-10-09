package com.triandamai.converter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public  class AppUser {
    private static String KEY_CURRENTUSER = "zzzzabx";


    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private static AppUser appUser;

    public AppUser(Context context) {
        String KEY_PERSISTANCE = "zzabxx";
        sharedPreferences  = context.getSharedPreferences(KEY_PERSISTANCE,0);
        editor = sharedPreferences.edit();
    }
    public AppUser() {
        String KEY_PERSISTANCE = "zzabxx";
        sharedPreferences  = BaseApplication.getContext().getSharedPreferences(KEY_PERSISTANCE,0);
        editor = sharedPreferences.edit();
    }



    @SuppressLint("CommitPrefEdits")
    public static AppUser initialize(Context context){
        if(appUser == null){
            appUser = new AppUser(context);
        }
     return  appUser;
    }
    public  static AppUser getInstance(){
        return new AppUser();
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
    public void signOut(){
        editor.clear();
        editor.apply();
    }
}
