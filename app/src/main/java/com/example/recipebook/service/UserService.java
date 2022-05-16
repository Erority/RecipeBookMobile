package com.example.recipebook.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.recipebook.model.PostUser;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {

    private Retrofit retrofit;

    private String BASE_URL = "http://94.228.124.99:5500/api/";
    private UserCall userCall;


    private MutableLiveData<User> authUser = new MutableLiveData<>();

    public UserService(String email, String password) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder builder = request.newBuilder().header("Authorization",
                        Credentials.basic(email, password));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userCall = retrofit.create(UserCall.class);
    }

    public UserService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userCall = retrofit.create(UserCall.class);
    }


    public void postUser(String name, String password, String email, String phone) {

        PostUser postUser = new PostUser(
                name,
                phone,
                null);

        userCall.postUser(
                email,
                password, postUser)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if (response.isSuccessful()) {
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });
    }

    public void getUser() {
        userCall.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                authUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void putUser(User user){
        userCall.putUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.isSuccessful())
                    System.out.println("DEBUG: User was putting");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<User> getAuthUser() {
        return authUser;
    }
}
