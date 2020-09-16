package com.triandamai.ui.introscreen;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.triandamai.core.MyUser;
import com.triandamai.data.services.Apiservice;
import com.triandamai.domain.MyConverter;
import com.triandamai.domain.UserModel;
import com.triandamai.domain.request.LoginRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Apiservice apiservice = Apiservice.Factory.create();
    private MyConverter converter = MyConverter.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyUser.getInstance()
                .setListener(signInListener)
                .setListener(signOutListener)
                .setListener(dataListener)
                .getCurrentUser();
        LoginRequest req = new LoginRequest();
        req.setPassword("trianDamai");
        req.setUsername("trianDamai");
        apiservice.prosesLogin(req)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        converter.check(response);
                        List<UserModel> u =  converter.getData(UserModel.class);
                        Log.e("tes",u.toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
        
    }
    private MyUser.signIn signInListener = new MyUser.signIn() {
        @Override
        public void onUserSignInListener(@NonNull UserModel user) {

        }
    };
    private MyUser.signOut signOutListener = new MyUser.signOut() {
        @Override
        public void onSignOutListener() {

        }
    };

    private MyUser.data dataListener = new MyUser.data() {
        @Override
        public void onDataChangeListener(@NonNull Object o) {

        }
    };
}
