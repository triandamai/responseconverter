/*
*   MyConverter
*   Author Trian Damai
*   Contoh json:
*/
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
     * initialze all app
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
    /**
     *
     * get succes request
     */

    public boolean success(){
        return response.isSuccessful();
    }

    /*
    *
    * get code from response status, and data stattus
    * */
    public boolean responsecode(){
            return cek(response.code());
    }

    public int getCodeBody() throws Exception {
        assert response.body() != null;
        JSONObject obj = new JSONObject(response.body().string());
        return  obj.getInt(RES_CODE);
    }
    public boolean responsebodyok() throws Exception {
        return this.responsecode() && cek(getCodeBody());
    }
    /*
    * di kasus ketika response code bukan 200/201
    * maka kita perlu mengambil error body untuk mengetahui isi errornya
    * */
    public String getEroroBody() throws IOException {
        assert response.errorBody() != null;
        return response.errorBody().string();
    }


    /*
    * Core converter
    * untuk mengkonversi data json ke class
    *  - SingleData = Untuk data berupa Object JSON
    *  - Data = untuk data berupa Object Array JSON
    * */
    public   <T> T geSingletData(Class<T> tClass, onHasData hasData){
        Object o = new Object();
        try {
            if(responsecode()) {
                if (responsebodyok()) {
                    assert response.body() != null;
                    JSONObject obj = new JSONObject(response.body().string());
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
            if(hasData != null) {
                hasData.onError(Objects.requireNonNull(e.getMessage()));
            }
        }
        return ((T)o);
    }
    public <T> List<T> getData(Class<T> tClass, onHasManyData hasData){
        List<T> o = new ArrayList<>();
        try {
            if(this.responsecode()){
                if(responsebodyok()) {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONArray jsonArray = obj.getJSONArray(RES_DATA);
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
        default <T> void onData(@NonNull Object data, Class<T> tClass){
            tClass.cast(data);
        };

    }
    public interface onHasManyData{
        void onError(@NonNull String errorBody);
        default <T> void onData(List<T> data, Class<T> tClass){
            tClass.cast(data);
        };

    }

}
