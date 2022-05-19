package com.example.recipebook.service;

import com.example.recipebook.model.PostRecipe;
import com.example.recipebook.model.PostUser;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeCall {

    @GET("Recipes")
    Call<List<Recipe>> getAllRecipes();

    @GET("Users/FavoriteRecipes")
    Call<List<Recipe>> getFavoriteRecipes();

    @POST("Users/Recipes")
    Call<Recipe> postRecipe(@Body PostRecipe postRecipe);

    @DELETE("Users/Recipes/{id}")
    Call<Recipe> deleteRecipe(@Path("id") int id);

    @DELETE("Users/FavoriteRecipes/{id}")
    Call<Recipe> deleteFavouriteRecipe(@Path("id") int id);

    @PUT("Users/FavoriteRecipes/{id}")
    Call<Void> addFavouriteRecipes(@Path("id") int id);
}
