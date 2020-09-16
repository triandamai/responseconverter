package com.triandamai.data.services;


import com.triandamai.domain.request.LoginRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Apiservice {
    String BASE_URL = "http://10.200.158.90:8000/";
    String accept_urlencoded = "Accept: application/x-www-form-urlencoded";
    String accept_json = "Accept: application/json";

    class Factory {
        public static Apiservice create() {
            return PandaFactory.createService(Apiservice.class);
        }
    }


    @Headers({accept_json})
    @POST("/users/login")
    Call<ResponseBody> prosesLogin(@Body LoginRequest req);


}
