package com.triandamai.converter;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.triandamai.converter.ApiHandler.Cek;


/**
*   MyConverter
*   Author Trian Damai
*   Contoh json:
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
    protected static final String RES_CODE = "status";
    protected static final String RES_DATA = "data";



    /**
    * sementara pake gson dulu
    *
    * */
    private Gson gson;
    /**
    * response disini
    * */
    private Response<ResponseBody> response = null;

    /**
     * constructor
     */

    protected MyConverter(){
        this.gson = new Gson();
    }
    protected MyConverter create(){
        return this;
    }
    public MyConverter check(Response<ResponseBody> response){
        this.response = response;
        return this;
    }
    public boolean success(){
        return response.isSuccessful();
    }

    public boolean responsecode(){
            return Cek(response.code());
    }

    public String getEroroBody() throws IOException {
        assert response.errorBody() != null;
        return response.errorBody().string();
    }
    public boolean responsebodyok() throws Exception {
         return this.responsecode() && Cek(getCodeBody());
    }
    public String getCodeBody() throws Exception {
        assert response.body() != null;
        String string = response.body().string();
        JSONObject obj = new JSONObject(string);
        Object rescode = obj.getInt(RES_CODE);
        if(String.class.isAssignableFrom((Class<String>) rescode)){
            return getStringCodeBody((String) rescode);
        }else {
            return String.valueOf(getIntCodeBody((Integer) rescode));
        }
    }
    public String getIntCodeBody(int res) {

        return String.valueOf(res);
    }

    public String getStringCodeBody(String code) {
     return String.valueOf(code);
    }

    public   <T> T geSingletData(Class<T> tClass, onHasData hasData){
        Object o = null;
        try {
            if(responsecode()) {
                if (responsebodyok()) {
                    assert response.body() != null;
                    String str = response.body().string();
                    JSONObject obj = new JSONObject(str);
                    o = gson.fromJson(obj.getString(RES_DATA), tClass);
                    if (hasData != null) {
                        hasData.onData(o, tClass);
                    }
                } else {
                    if (hasData != null) {
                        hasData.onError("RESPONSE BODY = " + getCodeBody());
                    }
                }
            }else {
                if(hasData != null) {
                    hasData.onError("RESPONSE CODE = " + response.code() + getEroroBody());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if(hasData != null) {
                hasData.onError(Objects.requireNonNull(e.getMessage()));
            }
        }
        return ((T)o);
    }
    public   <T> List<T> getData(Class<T> tClass, onHasManyData hasData){
        List<T> o = new ArrayList<>();
        try {
            if(this.responsecode()){
                if(responsebodyok()) {
                    String str = response.body().string();
                    JSONObject obj = new JSONObject(str);
                    JSONArray jsonArray = obj.getJSONArray(RES_DATA);
                    o = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object a = gson.fromJson(jsonArray.get(i).toString(), tClass);
                        o.add((T) a);
                    }
                    if (hasData != null) {
                        hasData.onData(o, tClass);
                    }
                }else {
                    if(hasData != null){
                        hasData.onError("RESPONSE BODY = "+getCodeBody());
                    }

                }
            }else {
                if(hasData != null){
                    hasData.onError("RESPONSE CODE "+getCodeBody()+getEroroBody());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(hasData != null) {
                hasData.onError(Objects.requireNonNull(e.getMessage()));
            }
        }
        return o ;
    }

    public interface onHasData{
        void onError(@NonNull  String errorBody);
        default <T> T onData(@NonNull Object data, Class<T> tClass){
            return tClass.cast(data);
        };

    }
    public interface onHasManyData{
        void onError(@NonNull String errorBody);
        default <T> List<T> onData(List<T> data,Class<T> tClass){
           return (List<T>) tClass.cast(data);
        };

    }

}
