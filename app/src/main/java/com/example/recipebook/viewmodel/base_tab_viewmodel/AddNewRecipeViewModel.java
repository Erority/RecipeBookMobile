package com.example.recipebook.viewmodel.base_tab_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipebook.model.Recipe;
import com.example.recipebook.service.RecipeService;
import com.example.recipebook.view.add_new_recipe.AddNewRecipeActivity;

public class AddNewRecipeViewModel extends ViewModel {
    private RecipeService recipeService;

    private MutableLiveData<Recipe> recipeToAdd = new MutableLiveData<Recipe>();

    public AddNewRecipeViewModel(){
        recipeService = new RecipeService();
    }

    public void addRecipe() {

    }

    public MutableLiveData<Recipe> getRecipeToAdd() {
        return recipeToAdd;
    }
}
