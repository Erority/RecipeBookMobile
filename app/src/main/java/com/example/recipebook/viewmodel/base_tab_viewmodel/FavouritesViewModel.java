package com.example.recipebook.viewmodel.base_tab_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipebook.model.Recipe;
import com.example.recipebook.service.RecipeService;

import java.util.List;

public class FavouritesViewModel extends ViewModel {

    private RecipeService recipeService;

    private MutableLiveData<List<Recipe>> recipesList = new MutableLiveData<>();


    public FavouritesViewModel(){
        recipeService = new RecipeService();
        recipesList = recipeService.getFavouritesRecipes();
    }

    public MutableLiveData<List<Recipe>> getRecipesList() {
        return recipesList;
    }
}
