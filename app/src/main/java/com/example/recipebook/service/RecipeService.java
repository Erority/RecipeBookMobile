package com.example.recipebook.service;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.recipebook.interfaces.PutCallBack;
import com.example.recipebook.model.PostRecipe;
import com.example.recipebook.model.PostUser;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private String BASE_URL = "http://94.228.124.99:5500/api/";

    public RecipeService(String email, String password) {

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

        recipeCall = retrofit.create(RecipeCall.class);
    }

    private MutableLiveData<List<Recipe>> favouriteRecipes = new MutableLiveData<>();
    public void callFavouritesRecipes() {

        recipeCall.getFavoriteRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful())
                    favouriteRecipes.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

    MutableLiveData<Recipe> deleteRecipe = new MutableLiveData<>();

    public void deleteRecipe(int id){

        recipeCall.deleteRecipe(id).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, retrofit2.Response<Recipe> response) {
                if(response.isSuccessful()){
                    deleteRecipe.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
    }



    public MutableLiveData<Recipe> deleteFavouriteRecipe(int id){

        recipeCall.deleteFavouriteRecipe(id).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, retrofit2.Response<Recipe> response) {
                if(response.isSuccessful()){
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });

        return deleteRecipe;
    }


    private MutableLiveData<List<Recipe>> allRecipes = new MutableLiveData<>();

    public void callAllRecipe() {
        recipeCall.getAllRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful())
                    allRecipes.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

    public void updateFavouriteRecipe(){
        recipeCall.getFavoriteRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if(response.isSuccessful())
                    favouriteRecipes.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }


    public void updateRecipesList() {
        recipeCall.getAllRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    allRecipes.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

    }

    private MutableLiveData<Recipe> postRecipe = new MutableLiveData<>();

    public void postRecipe(String nameRecipe, int minutes, int portions, String content, String image) {

        PostRecipe postRecipeModel = new PostRecipe(nameRecipe, content, portions, minutes, image);

        recipeCall.postRecipe(postRecipeModel).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, retrofit2.Response<Recipe> response) {
                if(response.isSuccessful())
                    postRecipe.setValue(response.body());

            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
    }

    public void addToFavourites(int id, PutCallBack callBack){
        recipeCall.addFavouriteRecipes(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.code() == 204)
                    callBack.OnSuccess(true);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Recipe>> getAllRecipes() {return allRecipes;}

    public MutableLiveData<Recipe> getPostRecipe() {
        return postRecipe;
    }

    public MutableLiveData<Recipe> getDeleteRecipe() {
        return deleteRecipe;
    }

    public MutableLiveData<List<Recipe>> getFavouriteRecipes() {
        return favouriteRecipes;
    }
}
