package com.example.recipebook.viewmodel.base_tab_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipebook.interfaces.PutCallBack;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.model.User;
import com.example.recipebook.service.RecipeService;
import com.example.recipebook.service.UserService;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<Recipe> delFavouriteRecipe = new MutableLiveData<>();
    private MutableLiveData<Recipe> delRecipe = new MutableLiveData<>();
    private MutableLiveData<List<Recipe>> favouritesRecipes = new MutableLiveData<List<Recipe>>();
    private MutableLiveData<User> recipeAuthor = new MutableLiveData<>();

     private Recipe currentRecipe;
    private MutableLiveData<Boolean> hasInFavourites = new MutableLiveData<>();

    private RecipeService recipeService;
    private UserService userService;

    public RecipeViewModel(){
    }

    public void deleteFromFavourites(int id){
        delFavouriteRecipe = recipeService.deleteFavouriteRecipe(id);
        hasInFavourites.setValue(false);
    }

    public void deleteRecipe(int id){
        recipeService.deleteRecipe(id);
    }

    public void addToFavourites(int id) {
        recipeService.addToFavourites(id, new PutCallBack() {
            @Override
            public void OnSuccess(boolean value) {
                hasInFavourites.setValue(true);
            }
        });
    }

    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
        delRecipe = recipeService.getDeleteRecipe();
        favouritesRecipes = recipeService.getFavouriteRecipes();

        recipeService.callFavouritesRecipes();

        hasInFavourites.setValue(false);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        userService.getUserById(currentRecipe.getUserId());

        recipeAuthor = userService.getUserById();
    }

    public MutableLiveData<User> getRecipeAuthor() {
        return recipeAuthor;
    }

    public MutableLiveData<Recipe> getDelRecipe() {
        return delRecipe;
    }

    public MutableLiveData<List<Recipe>> getFavouritesRecipes() {
        return favouritesRecipes;
    }

    public MutableLiveData<Recipe> getDelFavouriteRecipe() {
        return delFavouriteRecipe;
    }

    public MutableLiveData<Boolean> getHasInFavourites() {
        return hasInFavourites;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }
}
