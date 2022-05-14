package com.example.recipebook.service;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.recipebook.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;
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

public class RecipeService {

    private Retrofit retrofit;
    private RecipeCall recipeCall;
    private SharedPreferences mSettings;

    private String BASE_URL = "http://94.228.124.99:5500/api/";

    public RecipeService() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder builder = request.newBuilder().header("Authorization",
                        Credentials.basic("test@test.test", "a"));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipeCall = retrofit.create(RecipeCall.class);
    }

    public MutableLiveData<List<Recipe>> getFavouritesRecipes() {
        MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

        recipeCall.getFavoriteRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful())
                    recipes.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

        return recipes;
    }

    public MutableLiveData<List<Recipe>> getAllRecipes() {
        MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

        recipeCall.getAllRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful())
                    recipes.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

        return recipes;
    }
}
