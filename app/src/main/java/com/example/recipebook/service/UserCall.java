package com.example.recipebook.service;

import com.example.recipebook.model.PostUser;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserCall {

    @GET("Users")
    Call<User> getUser();

    @POST("Users")
    Call<User> postUser(@Query("email") String email, @Query("password") String password, @Body PostUser postUserData);
}
