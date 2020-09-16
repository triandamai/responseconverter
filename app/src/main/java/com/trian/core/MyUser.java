package com.trian.core;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.trian.domain.UserModel;

public class MyUser {
    public final static String KEY_PREF = "zzzbba";
    public final static String KEY_CURRENT_USER = "bbaazzxx";
    public final static String KEY_DATA = "ddttaa12";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private signIn signIn;
    private signOut signOut;
    private data data;
    private Gson gson = new Gson();

    @SuppressLint("CommitPrefEdits")
    public MyUser() {
        if(sharedPreferences == null){
            sharedPreferences = BaseApp.getAppContext().getSharedPreferences(KEY_PREF,0);
            edit = sharedPreferences.edit();
        }

    }

    public static MyUser getInstance(){

       return new MyUser();
    }
    public  MyUser setListener(signIn i, signOut o,data d){
        this.signIn = i;
        this.signOut = o;
        this.data = d;

        return this;
    }
    public MyUser setListener(signIn i){
        this.signIn = i;
        return this;
    }
    public MyUser setListener(signOut o){
        this.signOut = o;
        return this;
    }
    public MyUser setListener(data d){
        this.data = d;
        return this;
    }
    public void setCurrentUser(UserModel val){
        try{
            edit.putString(KEY_CURRENT_USER,gson.toJson(val));
            edit.apply();
            if(signIn != null){
                signIn.onUserSignInListener(val);
            }
        }catch (NullPointerException e){
            throw new IllegalArgumentException("MyUser : "+e.getMessage());
        }
    }
    public UserModel getCurrentUser(){
        try {
            return gson.fromJson(sharedPreferences.getString(KEY_CURRENT_USER,null),UserModel.class);
        }catch (NullPointerException e){
            throw new IllegalArgumentException("MyUser : "+e.getMessage());
        }
    }
    public void signOut(){
        edit.remove(KEY_CURRENT_USER);
        edit.apply();
        if(signOut != null){
            signOut.onSignOutListener();
        }
    }
    public <T> void setData(String key,Object o,Class<T> val){
       if(o.getClass().isAssignableFrom(val)){

           edit.putString(key,gson.toJson(o));
           edit.apply();
           if(data != null){
               data.onDataChangeListener(o);
           }
       }else {
           throw new IllegalArgumentException("class tidak valid");
       }
    }
    public <T> T getData(String key,Class<T> val){
      String s = sharedPreferences.getString(key,"");
      Object obj = gson.fromJson(s,val);
      if(obj.getClass().isAssignableFrom(val)) {
          return val.cast(obj);
      }else {
          throw new IllegalArgumentException("class tidak valid");
      }
    }
    public interface signOut{
        void onSignOutListener();
    }
    public interface signIn{
        void onUserSignInListener(@NonNull UserModel user);
    }
    public interface data{
        void onDataChangeListener(@NonNull Object o);
    }
}
