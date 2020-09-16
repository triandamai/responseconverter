package com.trian.domain;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.trian.core.ApiHandler.Cek;

/*
*   MyConverter
*   Author Trian Damai
*   Di Class ini akan digunakan sebagai converter dari response retrofit tanpa perlu banyak baris code
*   Contoh kita punya json:
*  {
    "status": 200,
    "data": [
        {
            "id_user": "sgdfhgjhjkl",
            "user_name": "Trian",
            "user_email": "trian@gmail.com"
            ....
        }
    ],
    "success": true,
    "message": "Berhasil"
}
*
* kita hanya perlu menggunakan method {@getData()} dan {@getSingleData()}
* untuk status,success,message dll tidak perlu di convert2 lagi dan menghindari null pointer
*
* Cara pakai
*   jadikan class ini superclass ubah RED_CODE,RES_DATA dll sesuai dengan json kalian
* */
public class MyConverter {

    public static final String RES_CODE = "status";
    public static final String RES_DATA = "data";
    private Gson gson;
    private Response<ResponseBody> response = null;
    public MyConverter(){
        this.gson = new Gson();
    }
    public static MyConverter create(){
        return new MyConverter();
    }
    public MyConverter check(Response<ResponseBody> response){
        this.response = response;
        return this;
    }
    public boolean success(){
        return response.isSuccessful();
    }
    public boolean responseok(){
        if(response != null){
            if(!Cek(response.code())){
                try {
                    assert response.errorBody() != null;
                    Log.e("Eror Body : ",response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Cek(response.code());
        }else {
            return false;
        }
    }
    public boolean responsebodyok(){
        try {
            if(response != null) {
                String string = response.body().string();
                JSONObject obj = new JSONObject(string);
                int rescode = obj.getInt(RES_CODE);
                return Cek(rescode);
            }else {
                return false;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
    public <T> T geSingletData(Class<T> tClass){
        Object o;
        try {
            if(responseok() && responsebodyok()) {
                    String str = response.body().string();
                    JSONObject obj = new JSONObject(str);
                    o = gson.fromJson(obj.getString(RES_DATA), tClass);
                    return tClass.cast(o);
            }else {
                return tClass.cast(new Object());
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return (T) new IllegalArgumentException("Error Convert Karena "+e.getMessage().toString());
        }
    }
    public <T> List<T> getData(Class<T> tClass){
        List<T> o = new ArrayList<>();
        try {
            String  str = response.body().string();
            JSONObject obj = new JSONObject(str);
            JSONArray jsonArray = obj.getJSONArray(RES_DATA);
            for (int i =0 ; i < jsonArray.length();i++) {
                Object a = gson.fromJson(jsonArray.get(i).toString(), tClass);
                    o.add((T) a);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return (List<T>) new IllegalArgumentException("Error Convert karena "+e.getMessage().toString());
        }
        return o ;
    }
}
