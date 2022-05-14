package com.example.recipebook.service;

import com.example.recipebook.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RecipeCall {

    @GET("Recipes")
    Call<List<Recipe>> getAllRecipes();


    @GET("Users/FavoriteRecipes")
    Call<List<Recipe>> getFavoriteRecipes();
}
