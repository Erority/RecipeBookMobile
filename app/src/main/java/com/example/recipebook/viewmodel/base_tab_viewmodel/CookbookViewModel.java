package com.example.recipebook.viewmodel.base_tab_viewmodel;

import android.util.Base64;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipebook.model.Recipe;
import com.example.recipebook.service.RecipeCall;
import com.example.recipebook.service.RecipeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CookbookViewModel extends ViewModel {
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private MutableLiveData<List<Recipe>> allRecipes = new MutableLiveData<>();
    private MutableLiveData<String> searchString = new MutableLiveData<>();
    private MutableLiveData<String> toastString = new MutableLiveData<>();

    private List<Recipe> allListRecipe;

    private RecipeService recipeService;
    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
    public MutableLiveData<String> getSearchString() {
        return searchString;
    }

    public MutableLiveData<String> getToastString() {
        return toastString;
    }

    public MutableLiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    public CookbookViewModel(){
    }

    public void filterCollection() {

        if(searchString.getValue() == null)
            return;

        List<Recipe> buffer = allListRecipe.stream()
                .filter(r -> r.getTitle().contains(searchString.getValue())).collect(Collectors.toList());


        if (buffer.size() <= 0) {
            recipes.setValue(allListRecipe);
            toastString.setValue("По вашему запросу нет резултатов");
        } else {
            recipes.setValue(buffer);
            toastString.setValue("Найдено " + buffer.size() + " элементов");
        }
    }



    public void updateRecipes() {
        recipeService.updateRecipesList();
    }

    public void setRecipeService(RecipeService recipeService){
        this.recipeService = recipeService;

        recipeService.callAllRecipe();
        recipes = recipeService.getAllRecipes();
        allRecipes = recipeService.getAllRecipes();
    }

    public void setAllListRecipe(List<Recipe> allListRecipe) {
        if(this.allListRecipe == null)
            this.allListRecipe = allListRecipe;
    }
}