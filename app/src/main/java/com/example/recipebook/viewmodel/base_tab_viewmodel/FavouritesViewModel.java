package com.example.recipebook.viewmodel.base_tab_viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;
import com.example.recipebook.service.RecipeService;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

public class FavouritesViewModel extends ViewModel {

    private RecipeService recipeService;

    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private MutableLiveData<String> searchString = new MutableLiveData<>();
    private MutableLiveData<List<Recipe>> allRecipes = new MutableLiveData<>();
    private MutableLiveData<String> toastString = new MutableLiveData<>();


    public FavouritesViewModel(){

        recipeService = new RecipeService();
        recipes = recipeService.getFavouritesRecipes();
        allRecipes = recipeService.getFavouritesRecipes();
    }

    public void filterCollection() {
        List<Recipe> buffer = allRecipes.getValue().stream()
                .filter(r -> r.getTitle().contains(searchString.getValue())).collect(Collectors.toList());

        if (buffer.size() <= 0) {
            recipes.setValue(allRecipes.getValue());

            toastString.setValue("По вашему запросу нет резултатов");
        } else
            recipes.setValue(buffer);
    }


    public MutableLiveData<List<Recipe>> getRecipesList() {
        return recipes;
    }

    public MutableLiveData<String> getSearchString() {
        return searchString;
    }


    public MutableLiveData<String> getToastString() {
        return toastString;
    }
}
