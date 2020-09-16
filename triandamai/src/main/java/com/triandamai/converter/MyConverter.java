package com.triandamai.converter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    protected MyConverter check(Response<ResponseBody> response){
        this.response = response;
        return this;
    }
    protected boolean success(){
        return response.isSuccessful();
    }

    protected boolean cekresponsecode(){

            if(!Cek(response.code())){
                try {
                    assert response.errorBody() != null;
                    Log.e("Eror Body : ",response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Cek(response.code());
    }
    protected boolean responsebodyok() throws JSONException, IOException {

          if(this.cekresponsecode()) {
              String string = response.body().string();
              JSONObject obj = new JSONObject(string);
              int rescode = obj.getInt(RES_CODE);
              return Cek(rescode);
          }else {
              return false;
          }
    }
    protected  <T> T geSingletData(Class<T> tClass, onHasData hasManyData){
        Object o = null;
        try {
            if(responsebodyok()) {
                    String str = response.body().string();
                    JSONObject obj = new JSONObject(str);
                    o = gson.fromJson(obj.getString(RES_DATA), tClass);
                   if(hasManyData != null){
                       hasManyData.onData(o,tClass);
                   }
            }else {
                return tClass.cast(new Object());
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            hasManyData.onError(e.getMessage());
        }
        return ((T)o);
    }
    protected  <T> List<T> getData(Class<T> tClass, onHasManyData hasData){
        List<T> o = new ArrayList<>();
        try {
            if(this.responsebodyok()){
                String  str = response.body().string();
                JSONObject obj = new JSONObject(str);
                JSONArray jsonArray = obj.getJSONArray(RES_DATA);
                o = new ArrayList<>();
                for (int i =0 ; i < jsonArray.length();i++) {
                    Object a = gson.fromJson(jsonArray.get(i).toString(), tClass);
                        o.add((T)a);
                }
                if(hasData != null) {
                    hasData.onData(o,tClass);
                }
            }else {
                if(hasData != null){
                    hasData.onError("Response code selain 200");
                }
                return  null;
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            hasData.onError(e.getMessage());
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
        <T> void onData(List<T> o,Class<T> tClass);

    }

}
