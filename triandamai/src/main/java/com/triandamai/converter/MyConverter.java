/*
*   MyConverter
*   Author Trian Damai
*   Contoh json:
*/
package com.triandamai.converter;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.triandamai.converter.model.ResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.triandamai.converter.ApiHandler.RES_CREATED;
import static com.triandamai.converter.ApiHandler.RES_INTERNALSERVERERROR;
import static com.triandamai.converter.ApiHandler.RES_METHODNOTALLOWED;
import static com.triandamai.converter.ApiHandler.RES_NOTACCEPTABLE;
import static com.triandamai.converter.ApiHandler.RES_NOTFOUND;
import static com.triandamai.converter.ApiHandler.RES_OK;
import static com.triandamai.converter.ApiHandler.RES_UNAUTHORIZED;
/**
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
* */
public abstract class MyConverter {

    /**
    * jadikan class ini superclass ubah RED_CODE,RES_DATA dll sesuai dengan json kalian
    *  */
    protected static final String RES_DATA = "data";

    public enum typeGetData{
        Object,
        List
    }
    /**
    * sementara pake gson dulu
    *
    * */
    private Gson gson;
    /**
    * response disini
    * */
    private Response<ResponseBody> response = null;
    private Class tClass;
    private whenIsDoneListener whenIsDoneListener;
    private typeGetData tipedata;

    /**
     * constructor
     * initialze all app
     */

    public MyConverter createWithResponse(@NonNull Response<ResponseBody> response){
        this.gson = new Gson();
        this.response = response;
        return this;
    }
    public <T> MyConverter setClassToConvert(@NonNull Class<T> tClass){
        this.tClass = tClass;
        return this;
    }
    public MyConverter setTypeData(@NonNull typeGetData tipe){
        this.tipedata = tipe;
        return this;
    }
    public MyConverter setWhenIsDone(@NonNull whenIsDoneListener listener){
        this.whenIsDoneListener = listener;
        return this;
    }
    public MyConverter check() {
        if(whenIsDoneListener !=
                null) {
            if (success()) {
                try {
                switch (response.code()) {
                    case RES_CREATED:
                    case RES_OK:
                        if (tipedata == typeGetData.List) {
                                whenIsDoneListener.onData(response.code(), null,getListData());
                            } else {
                                whenIsDoneListener.onData(response.code(), getObjectData(true),null);
                            }
                            break;
                    case RES_NOTACCEPTABLE:
                            whenIsDoneListener.onResponse(RES_UNAUTHORIZED, getObjectData(false), response.body().string());
                        break;
                    case RES_UNAUTHORIZED:
                            whenIsDoneListener.onError("Tidak terauthentikasi 401 "+response.errorBody().string());
                        break;
                    case RES_INTERNALSERVERERROR:
                            whenIsDoneListener.onError("Internal server Error 500 "+response.errorBody().string());
                        break;
                    case RES_METHODNOTALLOWED:
                            whenIsDoneListener.onError("Method Not Allowed "+RES_METHODNOTALLOWED+" "+response.errorBody().string());
                        break;
                    case RES_NOTFOUND:
                            whenIsDoneListener.onError("Not found "+RES_NOTFOUND+" "+response.errorBody().string());
                        break;
                    }
                }catch(Exception e){
                    whenIsDoneListener.onError("Failed : "+e.getMessage());
                }
            }else {
                whenIsDoneListener.onError("Tidak berhasil request data ! error:"+response.toString());
            }
        }else {
            throw new IllegalArgumentException("Listener kosong woi!!");
        }
        return this;
    }
    /**
     *
     * get succes request
     */

    protected boolean success(){
        return response.isSuccessful();
    }
    /*
    * Core converter
    * untuk mengkonversi data json ke class
    *  - SingleData = Untuk data berupa Object JSON
    *  - Data = untuk data berupa Object Array JSON
    * */
    protected  <T> T getObjectData( boolean isOk) {
        Object o;
        JSONObject obj = null;
        try {
            obj = new JSONObject(response.body().string());
            if(isOk) {
                o = gson.fromJson(obj.getString(RES_DATA), tClass);
            }else {
                o = gson.fromJson(response.body().string(),ResponseModel.class);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            o = null;
        }

        return ((T)o);
    }


    protected  <T> List<T> getListData() {
        List<Object> o = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(response.body().string());
            JSONArray jsonArray = null;
            jsonArray = obj.getJSONArray(RES_DATA);
            for (int i = 0; i < jsonArray.length(); i++) {
                Object a = gson.fromJson(jsonArray.get(i).toString(), tClass);
                o.add((T) a);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            o = null;
        }
        return ((List<T>)o) ;
    }

    public interface whenIsDoneListener{
        <T> void onData(int responseCode,T obj,List<T> listobj);
        <T> void onResponse(int responseCode,T data,String responseBody);
        void onError(String errorBody);
    }

}
