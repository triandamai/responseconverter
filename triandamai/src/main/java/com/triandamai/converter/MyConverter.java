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
import java.util.Objects;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.triandamai.converter.ApiHandler.RES_CREATED;
import static com.triandamai.converter.ApiHandler.RES_INTERNALSERVERERROR;
import static com.triandamai.converter.ApiHandler.RES_METHODNOTALLOWED;
import static com.triandamai.converter.ApiHandler.RES_NOTFOUND;
import static com.triandamai.converter.ApiHandler.RES_OK;
import static com.triandamai.converter.ApiHandler.RES_UNAUTHORIZED;
import static com.triandamai.converter.ApiHandler.cek;
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

    public  <T> MyConverter createWithResponse(Response<ResponseBody> response){
        this.gson = new Gson();
        this.response = response;
        return this;
    }
    public  <T> MyConverter setClassToConvert(Class<T> tClass){
        this.tClass = tClass;
        return this;
    }
    public  <T> MyConverter setTypeData(typeGetData tipe){
        this.tipedata = tipe;
        return this;
    }
    public  <T> MyConverter whenIsDone(whenIsDoneListener listener){
        this.whenIsDoneListener = listener;
        return this;
    }
    public MyConverter check() throws Exception {
        if(whenIsDoneListener != null) {
            if (success()) {
                switch (response.code()) {
                    case RES_OK:
                        if(tipedata == typeGetData.List){
                            whenIsDoneListener.onSuccess(response.code(),getListData());
                        }else {
                            whenIsDoneListener.onSuccess(response.code(),getObjectData(true));
                        }
                        break;
                    case RES_CREATED:
                        if(tipedata == typeGetData.List){
                            whenIsDoneListener.onSuccess(response.code(),getListData());
                        }else {
                            whenIsDoneListener.onSuccess(response.code(),getObjectData(true));
                        }
                        break;
                    case RES_UNAUTHORIZED:
                        whenIsDoneListener.onResponse(RES_UNAUTHORIZED,getObjectData(false),response.body().string());
                        break;
                    case RES_INTERNALSERVERERROR:
                        whenIsDoneListener.onError("Server 500");
                        break;
                    case RES_METHODNOTALLOWED:
                        whenIsDoneListener.onError("method not allowed ");
                        break;
                    case RES_NOTFOUND:
                        whenIsDoneListener.onError("not found");
                        break;
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

    public boolean success(){
        return response.isSuccessful();
    }
    /*
    * Core converter
    * untuk mengkonversi data json ke class
    *  - SingleData = Untuk data berupa Object JSON
    *  - Data = untuk data berupa Object Array JSON
    * */
    protected  <T> T getObjectData( boolean isOk) throws Exception {
        Object o;
        JSONObject obj = new JSONObject(response.body().string());
        if(isOk) {
            o = gson.fromJson(obj.getString(RES_DATA), tClass);
        }else {
            o = gson.fromJson(response.body().string(),ResponseModel.class);
        }
        return ((T)o);
    }


    protected  <T> List<T> getListData() throws Exception {
        List<T> o = new ArrayList<>();
        JSONObject obj = new JSONObject(response.body().string());
        JSONArray jsonArray = obj.getJSONArray(RES_DATA);
        for (int i = 0; i < jsonArray.length(); i++) {
            Object a = gson.fromJson(jsonArray.get(i).toString(), tClass);
            o.add((T) a);
        }
        return o ;
    }

    public interface whenIsDoneListener{
        <T> void onSuccess(int responseCode ,List<T> data);
        <T> void onSuccess(int responseCode,T data);
        <T> void onResponse(int responseCode,T data,String responseBody);
        void onError(String errorBody);
    }

}
